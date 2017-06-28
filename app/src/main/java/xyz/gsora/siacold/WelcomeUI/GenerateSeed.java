package xyz.gsora.siacold.WelcomeUI;


import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mtramin.rxfingerprint.EncryptionMethod;
import com.mtramin.rxfingerprint.RxFingerprint;
import io.reactivex.disposables.Disposable;
import siawallet.Wallet;
import xyz.gsora.siacold.Crypto;
import xyz.gsora.siacold.R;
import xyz.gsora.siacold.SiaCold;
import xyz.gsora.siacold.Utils;

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
                        if (RxFingerprint.isAvailable(getActivity())) {
                            Disposable disposable = RxFingerprint.encrypt(EncryptionMethod.RSA, getActivity(), SiaCold.KEY_NAME, w.getSeed())
                                    .subscribe(encryptionResult -> {
                                        switch (encryptionResult.getResult()) {
                                            case FAILED:
                                                Utils.toast(getActivity(),"Fingerprint not recognized, try again!");
                                                break;
                                            case HELP:
                                                Utils.toast(getActivity(), encryptionResult.getMessage());
                                                break;
                                            case AUTHENTICATED:
                                                Utils.toast(getActivity(), "Successfully authenticated!");
                                                break;
                                        }
                                    }, throwable -> {
                                        Log.e("ERROR", "authenticate", throwable);
                                        Utils.toast(getActivity(), throwable.getMessage());
                                    });
                            disposable.dispose();
                        }
                    }
                });

        AlertDialog ad = builder.create();
        ad.show();
    }


}
