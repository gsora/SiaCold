package xyz.gsora.siacold.WelcomeUI;


import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.gsora.siacold.Crypto;
import xyz.gsora.siacold.R;
import xyz.gsora.siacold.SiaCold;

import static android.app.Activity.RESULT_OK;

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
            c.tryDecrypt();
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SiaCold.REQUEST_CODE:
                // Challenge completed, proceed with using cipher
                if (resultCode == RESULT_OK) {
                    if (c.tryDecrypt()) {
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
