package xyz.gsora.siacold.WelcomeUI;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.gsora.siacold.General.Utils;
import xyz.gsora.siacold.MainActivity;
import xyz.gsora.siacold.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoToMasterUI extends Fragment {

    @BindView(R.id.gotoMain)
    Button gotoMain;

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
        gotoMain.setOnClickListener(v -> {
            SharedPreferences.Editor s = Utils.getSharedPreferences(getActivity(), "main").edit();
            s.putBoolean("setupDone", true);
            s.apply();
            startActivity(new Intent(getContext(), MainActivity.class));
            getActivity().finish();
        });
    }

}
