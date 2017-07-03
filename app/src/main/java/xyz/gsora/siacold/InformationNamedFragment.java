package xyz.gsora.siacold;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.*;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import net.glxn.qrgen.android.QRCode;
import xyz.gsora.siacold.AppPreferences.AppPreferencesActivity;
import xyz.gsora.siacold.General.*;


/**
 * A simple {@link Fragment} subclass, to handle wallet informations and such.
 */
public class InformationNamedFragment extends Fragment implements NamedFragment, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String FancyName = "Info";
    private static final String TAG = InformationNamedFragment.class.getSimpleName();

    @BindView(R.id.qr)
    ImageButton qr;

    @BindView(R.id.addressText)
    TextView addressText;

    @BindView(R.id.refreshInfo)
    SwipeRefreshLayout refreshInfo;

    private Crypto c;

    public InformationNamedFragment() {
        // Required empty public constructor
    }

    public static InformationNamedFragment newInstance() {
        return new InformationNamedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c = new Crypto(getActivity(), getContext(), this, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_information, container, false);
        ButterKnife.bind(this, v);

        qr.setImageBitmap(
                QRCode.from(getLastAddress().getAddress()).withSize(750, 750).bitmap()
        );

        qr.setOnClickListener(vr -> Utils.shareAddress(new Address(Utils.getQRAddressFromPrefs(getContext())), getContext()));

        String siavalue = Utils.getSharedPreferences(getContext(), "main").getString("siavalue", "0 SC");
        addressText.setText(siavalue);

        SharedPreferences asd = PreferenceManager.getDefaultSharedPreferences(getActivity());
        asd.registerOnSharedPreferenceChangeListener(this);

        refreshInfo.setEnabled(false);

        writeAmount();

        setHasOptionsMenu(true);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    @Override
    public String getFragmentFancyName() {
        return FancyName;
    }

    private Address getLastAddress() {
        return Utils.getRealm().where(Address.class).findAllSorted("id").last();
    }

    public void writeAmount() {
        refreshInfo.setEnabled(true);
        refreshInfo.setRefreshing(true);
        AddressesBalanceFetcher abf = new AddressesBalanceFetcher();

        abf.fetch(Utils.getRealm().copyFromRealm(Utils.getRealm().where(Address.class).findAllAsync()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(data -> {
                    String siaReadableCoins = SiaValue.readableCoins(data);
                    Utils.saveToPrefs(getContext(), "siavalue", siaReadableCoins);
                    addressText.setText(siaReadableCoins);
                    refreshInfo.setEnabled(false);
                    refreshInfo.setRefreshing(false);
                });
    }

    public void openSettings() {
        Intent i = new Intent(getActivity(), AppPreferencesActivity.class);
        startActivity(i);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.information_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                writeAmount();
                return true;
            case R.id.settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("qraddress")) {
            Log.d(TAG, "onSharedPreferenceChanged: called modify on qraddress");
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
            qr.setImageBitmap(
                    QRCode.from(sharedPref.getString("qraddress", "")).withSize(750, 750).bitmap()
            );
        }
    }
}
