package xyz.gsora.siacold;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass, to handle addresses and such.
 */
public class AddressesNamedFragment extends Fragment implements NamedFragment {

    private static final String FancyName = "Addresses";

    public AddressesNamedFragment() {
        // Required empty public constructor
    }

    public static AddressesNamedFragment newInstance() {
        AddressesNamedFragment fragment = new AddressesNamedFragment();
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
        return inflater.inflate(R.layout.fragment_addresses, container, false);
    }

    @Override
    public String getFragmentFancyName() {
        return FancyName;
    }

}
