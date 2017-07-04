package xyz.gsora.siacold.AppPreferences;

import android.content.Context;
import android.support.v7.preference.ListPreference;
import android.util.AttributeSet;
import io.realm.RealmResults;
import xyz.gsora.siacold.General.Address;
import xyz.gsora.siacold.General.Utils;

/**
 * Created by gsora on 7/2/17.
 *
 * Custom ListPreference for Realm.
 */
public class RealmAddressesListPreference extends ListPreference {

    private RealmResults<Address> realmData;
    private String[] entryValues;
    private String[] entries;

    public RealmAddressesListPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public RealmAddressesListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        realmData = Utils.getRealm().where(Address.class).findAllSorted("id");
        buildEntrySet();
        setEntries(entries);
        setEntryValues(entryValues);
    }

    private void buildEntrySet() {
        int i = 0;
        entries = new String[realmData.size()];
        entryValues = new String[realmData.size()];

        for(Address a : realmData) {
            entries[i] = a.getDescription();
            entryValues[i] = a.getAddress();
            i++;
        }
    }
}
