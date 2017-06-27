package xyz.gsora.siacold.WelcomeUI;


import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import siawallet.Wallet;
import xyz.gsora.siacold.Crypto;
import xyz.gsora.siacold.R;
import xyz.gsora.siacold.SiaCold;

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
    String seed;
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
        c = new Crypto(getActivity(), getContext(), this);
        seed = null;
        generateSeedButton.setOnClickListener(v -> {
            generateSeed(v);
        });
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

        builder.setView(dia)
                // Add action buttons
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        c.createKey();
                        seed = seedTxt.getText().toString();
                        c.tryEncrypt(seed.getBytes(StandardCharsets.UTF_8));
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
                    c.createKey();
                    if (c.tryEncrypt(seed.getBytes(StandardCharsets.UTF_8))) {
                        Toast.makeText(getActivity(), "tryEncrypt Success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "tryEncrypt Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "tryEncrypt Failed", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
