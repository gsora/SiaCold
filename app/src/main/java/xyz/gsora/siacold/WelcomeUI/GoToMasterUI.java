package xyz.gsora.siacold.WelcomeUI;


import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mtramin.rxfingerprint.EncryptionMethod;
import com.mtramin.rxfingerprint.RxFingerprint;
import io.reactivex.disposables.Disposable;
import xyz.gsora.siacold.Crypto;
import xyz.gsora.siacold.R;
import xyz.gsora.siacold.SiaCold;
import xyz.gsora.siacold.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoToMasterUI extends Fragment {

    private KeyguardManager keyguard;
    @BindView(R.id.button)
    Button button;
    Crypto c;

    public GoToMasterUI() {
        // Required empty public constructor
    }


    public static GoToMasterUI newInstance() {
        GoToMasterUI fragment = new GoToMasterUI();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_go_to_master_ui, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle bundle){
        super.onActivityCreated(bundle);
        keyguard = (KeyguardManager) getActivity().getSystemService(Context.KEYGUARD_SERVICE);
        c = new Crypto(getActivity(), getContext(), this);
        button.setOnClickListener(v -> {
            String decryptedValue = null;
            Disposable disposable = RxFingerprint.decrypt(EncryptionMethod.RSA, getActivity(), SiaCold.KEY_NAME, decryptedValue)
                    .subscribe(decryptionResult -> {
                        switch (decryptionResult.getResult()) {
                            case FAILED:
                                Utils.toast(getActivity(), "Fingerprint not recognized, try again!");
                                break;
                            case HELP:
                                Utils.toast(getActivity(), decryptionResult.getMessage());
                                break;
                            case AUTHENTICATED:
                                Utils.toast(getActivity(), "decrypted:\n" + decryptionResult.getDecrypted());
                                break;
                        }
                    }, throwable -> {
                        //noinspection StatementWithEmptyBody
                        if (RxFingerprint.keyInvalidated(throwable)) {
                            // The keys you wanted to use are invalidated because the user has turned off his
                            // secure lock screen or changed the fingerprints stored on the device
                            // You have to re-encrypt the data to access it
                        }
                        Log.e("ERROR", "decrypt", throwable);
                        Utils.toast(getActivity(), throwable.getMessage());
                    });
            disposable.dispose();
        });
    }
}
