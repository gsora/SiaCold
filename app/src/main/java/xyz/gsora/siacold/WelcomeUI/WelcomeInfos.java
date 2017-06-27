package xyz.gsora.siacold.WelcomeUI;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.gsora.siacold.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeInfos extends Fragment {

    private static String TAG = WelcomeInfos.class.getSimpleName();

    @BindView(R.id.bodyText)
    TextView bodyText;

    public WelcomeInfos() {
        // Required empty public constructor
    }

    public static WelcomeInfos newInstance() {
        WelcomeInfos fragment = new WelcomeInfos();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_welcome_infos, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedinstance) {
        super.onActivityCreated(savedinstance);
    }

}
