package xyz.gsora.siacold.Addresses;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import xyz.gsora.siacold.General.Address;
import xyz.gsora.siacold.General.Utils;
import xyz.gsora.siacold.NamedFragment;
import xyz.gsora.siacold.R;


/**
 * A simple {@link Fragment} subclass, to handle addresses and such.
 */
public class AddressesNamedFragment extends Fragment implements NamedFragment {

    private static final String FancyName = "Addresses";
    @BindView(R.id.addressesRecyclerView)
    RecyclerView addressesRecyclerView;

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
        View v = inflater.inflate(R.layout.fragment_addresses, container, false);
        ButterKnife.bind(this, v);

        RealmResults<Address> addresses = Utils.getRealm().where(Address.class).findAllAsync();
        addressesRecyclerView.setAdapter(new AddressesListAdapter(addresses, getActivity()));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.scrollToPosition(0);
        addressesRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(addressesRecyclerView.getContext(),
                layoutManager.getOrientation());
        addressesRecyclerView.addItemDecoration(dividerItemDecoration);

        setHasOptionsMenu(true);

        return v;
    }


    @Override
    public String getFragmentFancyName() {
        return FancyName;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.addresses_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
