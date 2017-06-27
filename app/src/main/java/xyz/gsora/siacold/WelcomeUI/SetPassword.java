package xyz.gsora.siacold.WelcomeUI;


import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.gsora.siacold.MainActivity;
import xyz.gsora.siacold.R;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetPassword extends Fragment {

    private static String TAG = SetPassword.class.getSimpleName();

    @BindView(R.id.triggerPassword)
    Button triggerPassword;

    @BindView(R.id.go)
    Button go;

    public SetPassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_set_password, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedinstance) {
        super.onActivityCreated(savedinstance);
        triggerPassword.setOnClickListener(v -> {
            clickPass(v);
        });

        go.setOnClickListener(v -> {
            go(v);
        });
    }


    public void clickPass(View v) {
        KeyguardManager k = (KeyguardManager) getActivity().getSystemService(Context.KEYGUARD_SERVICE);
        Intent s = k.createConfirmDeviceCredentialIntent(null, null);
        startActivityForResult(s, 500);
    }

    public void go(View v) {
        startActivity(new Intent(getContext(), MainActivity.class));
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: infos -> " + requestCode + " " + resultCode);
        if (requestCode == 500) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onActivityResult: correctly logged in");
            } else {
                Log.d(TAG, "onActivityResult: not correctly logged in");
            }
        }
    }
}
