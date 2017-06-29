package xyz.gsora.siacold.WelcomeUI;


import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
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
 */
public class GenerateSeed extends Fragment {

    private static String TAG = GenerateSeed.class.getSimpleName();

    @BindView(R.id.generateSeedButton)
    Button generateSeedButton;

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
    }

    public void generateSeed(View v) {
        Wallet w = new Wallet();
        try {
            w.generateSeed();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String seed = w.getSeed();

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
                    c = new Crypto(getActivity(), getContext(), thisFragment, seed.getBytes(StandardCharsets.UTF_8));
                    c.tryEncrypt();
                    try {
                        setSeed(new Wallet(), c);
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
                        Wallet w = new Wallet();
                        try {
                            setSeed(w, c);
                        } catch (NoDecryptedDataException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
    }

    private void setSeed(Wallet w, Crypto c) throws NoDecryptedDataException {
        c.tryDecrypt();
        w.setSeed(c.getDecryptedData());
        Realm r = Utils.getRealm();
        r.executeTransaction(realm -> {
            realm.insertOrUpdate(new Address(w.getAddress(Utils.incrementSeedInt(getContext(), "main"))));
        });
        ((WelcomeActivity) getActivity()).moveToNextPage();
    }


}
