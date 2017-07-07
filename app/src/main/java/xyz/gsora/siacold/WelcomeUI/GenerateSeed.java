package xyz.gsora.siacold.WelcomeUI;


import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import siawallet.Wallet;
import xyz.gsora.siacold.General.*;
import xyz.gsora.siacold.R;

import java.nio.charset.StandardCharsets;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 *
 * Generate seed friendly UI.
 */
public class GenerateSeed extends Fragment {

    private static String TAG = GenerateSeed.class.getSimpleName();

    @BindView(R.id.generateSeedButton)
    Button generateSeedButton;

    @BindView(R.id.importSeed)
    Button importSeed;

    KeyguardManager keyguard;
    Crypto c;

    public GenerateSeed() {
        // Required empty public constructor
    }


    public static GenerateSeed newInstance() {
        GenerateSeed fragment = new GenerateSeed();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_generate_seed, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        keyguard = (KeyguardManager) getActivity().getSystemService(Context.KEYGUARD_SERVICE);
        generateSeedButton.setOnClickListener(this::generateSeed);
        importSeed.setOnClickListener(this::showImportSeed);
    }

    public void showImportSeed(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dia = inflater.inflate(R.layout.import_seed, null);
        EditText e = (EditText) dia.findViewById(R.id.importSeedText);
        byte[] seedByte = e.getText().toString().getBytes(StandardCharsets.UTF_8);

        Fragment thisFragment = this;
        builder.setView(dia)
                // Add action buttons
                .setPositiveButton("Save", (dialog, id) -> {
                    c = new Crypto(getActivity(), getContext(), thisFragment, seedByte);
                    c.tryEncrypt();
                    try {
                        setSeed(c);
                    } catch (NoDecryptedDataException ee) {
                        ee.printStackTrace();
                    }
                });

        AlertDialog ad = builder.create();
        ad.show();
    }

    public void generateSeed(View v) {
        Wallet w = new Wallet();
        try {
            w.generateSeed();
        } catch (Exception e) {
            e.printStackTrace();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dia = inflater.inflate(R.layout.seed_dialog, null);

        TextView seedTxt = (TextView) dia.findViewById(R.id.seed);
        if(seedTxt != null) {
            seedTxt.setText(w.getSeed());
        }

        Fragment thisFragment = this;

        builder.setView(dia)
                // Add action buttons
                .setPositiveButton("Save", (dialog, id) -> {
                    String seed = w.getSeed();
                    c = new Crypto(getActivity(), getContext(), thisFragment, seed.getBytes(StandardCharsets.UTF_8));
                    c.tryEncrypt();
                    try {
                        setSeed(c);
                    } catch (NoDecryptedDataException e) {
                        e.printStackTrace();
                    }
                });

        AlertDialog ad = builder.create();
        ad.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SiaCold.REQUEST_CODE:
                // Challenge completed, proceed with using cipher
                if (resultCode == RESULT_OK) {
                    if (c.tryEncrypt()) {
                        try {
                            setSeed(c);
                        } catch (NoDecryptedDataException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
    }

    private void writeDefaultPreferences(String address) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor spEdit = sharedPref.edit();
        spEdit.putBoolean("askForPasscode", true);
        spEdit.putString("qraddress", address);
        spEdit.apply();
    }

    private void setSeed(Crypto c) throws NoDecryptedDataException {
        Wallet w = new Wallet();
        c.tryDecrypt();
        w.setSeed(c.getDecryptedData());
        Realm r = Utils.getRealm();
        String address = w.getAddress(Utils.incrementSeedInt(getContext(), "main"));
        r.executeTransaction(realm -> {
            realm.insertOrUpdate(new Address(address, "Main address", 0));
        });
        writeDefaultPreferences(address);
        ((WelcomeActivity) getActivity()).moveToNextPage();
    }


}
