package xyz.gsora.siacold;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass, to handle wallet informations and such.
 */
public class InformationNamedFragment extends Fragment implements NamedFragment {

    private static final String FancyName = "Info";

    public InformationNamedFragment() {
        // Required empty public constructor
    }

    public static InformationNamedFragment newInstance() {
        InformationNamedFragment fragment = new InformationNamedFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_information, container, false);
    }

    @Override
    public String getFragmentFancyName() {
        return FancyName;
    }

}
