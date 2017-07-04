package xyz.gsora.siacold.AppPreferences;


import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import io.realm.RealmResults;
import xyz.gsora.siacold.General.Address;
import xyz.gsora.siacold.General.SiaCold;
import xyz.gsora.siacold.General.Utils;
import xyz.gsora.siacold.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppPreferencesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppPreferencesFragment extends PreferenceFragmentCompat {

    public AppPreferencesFragment() {
        // Required empty public constructor
    }

    public static AppPreferencesFragment newInstance() {
        AppPreferencesFragment fragment = new AppPreferencesFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ListPreference listPreference = (ListPreference) findPreference("qraddress");

        RealmResults<Address> realmDataOrig = Utils.getRealm().where(Address.class).findAllSorted("id");
        List<Address> realmData = Utils.getRealm().copyFromRealm(realmDataOrig);
        int i = 0;
        String[] entries = new String[realmData.size()];
        String [] entryValues = new String[realmData.size()];

        for(Address a : realmData) {
            entries[i] = a.getDescription();
            entryValues[i] = a.getAddress();
            i++;
        }

        listPreference.setEntries(entries);
        listPreference.setEntryValues(entryValues);

        Preference p = findPreference("aboutMe");
        p.setOnPreferenceClickListener(k -> {
            String url = getString(R.string.megsoraxyz);
            Intent in = new Intent(Intent.ACTION_VIEW);
            in.setData(Uri.parse(url));
            startActivity(in);
            return false;
        });

        Preference d = findPreference("donate");
        d.setOnPreferenceClickListener(k -> {
            showDonationDialog();
            return false;
        });

        Preference k = findPreference("openlicenses");
        k.setOnPreferenceClickListener(o -> {
            new LibsBuilder()
                    .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                    .start(getActivity());
            return false;
        });
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.app_preferences);
    }

    private void copyAddressToClipboard(String address) {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("donation-address", address);
        clipboard.setPrimaryClip(clip);
        Utils.toast(getActivity(), "Address copied on your clipboard!");
    }

    private void showDonationDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dia = inflater.inflate(R.layout.donation_dialog, null);

        ImageView btc = (ImageButton) dia.findViewById(R.id.btc);
        btc.setOnClickListener(b -> {
            copyAddressToClipboard(SiaCold.BTC_ADDRESS);
        });

        ImageView eth = (ImageButton) dia.findViewById(R.id.eth);
        eth.setOnClickListener(e -> {
            copyAddressToClipboard(SiaCold.ETH_ADDRESS);
        });

        ImageView sia = (ImageButton) dia.findViewById(R.id.sia);
        sia.setOnClickListener(s -> {
            copyAddressToClipboard(SiaCold.SIA_ADDRESS);
        });

        android.support.v4.app.Fragment thisFragment = this;

        builder.setView(dia)
                // Add action buttons
                .setPositiveButton("Thank you!", (dialog, id) -> {});

        android.support.v7.app.AlertDialog ad = builder.create();
        ad.show();
    }

}
