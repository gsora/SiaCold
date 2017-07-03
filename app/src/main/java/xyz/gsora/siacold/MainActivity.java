package xyz.gsora.siacold;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import siawallet.Wallet;
import xyz.gsora.siacold.General.*;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean showAuthScreen = sharedPref.getBoolean("askForPasscode", true);

        if (showAuthScreen) {
            Crypto.showAuthenticationScreen(this, (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE));
        }

        fab.hide();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        fab.hide();
                        break;
                    case 1:
                        fab.show();
                        break;
                    default:
                        fab.hide();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fab.setOnClickListener((View view) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Get the layout inflater
            LayoutInflater inflater = this.getLayoutInflater();
            View dia = inflater.inflate(R.layout.description_add_address, null);

            builder
                    .setView(dia)
                    .setPositiveButton(
                            android.R.string.ok,
                            null
                    );

            AlertDialog ad = builder.create();
            ad.setOnShowListener(dialog -> {
                Button b = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(v -> {
                    EditText descriptionET = (EditText) dia.findViewById(R.id.addressDescriptionText);
                    String description = descriptionET.getText().toString();
                    if (description.length() <= 0) {
                        descriptionET.setError("Description can't be empty");
                    } else {
                        saveAddress(description);
                        ad.dismiss();
                    }
                });
            });
            ad.show();
        });
    }

    private void saveAddress(String description) {
        Crypto c = new Crypto(this, getApplicationContext(), null, null);
        c.tryDecrypt();
        Wallet w = new Wallet();
        try {
            w.setSeed(c.getDecryptedData());
            Realm r = Utils.getRealm();
            r.executeTransaction(realm -> {
                long lastInsertedId = realm.where(Address.class).findAllSorted("id").last().getId();
                realm.insertOrUpdate(new Address(w.getAddress(Utils.incrementSeedInt(this, "main")), description, lastInsertedId + 1));
            });
        } catch (NoDecryptedDataException e) {
            e.printStackTrace();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SiaCold.REQUEST_CODE:
                if (resultCode != RESULT_OK) {
                    Crypto.showAuthenticationScreen(this, (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE));
                }
                break;
        }
    }

}

