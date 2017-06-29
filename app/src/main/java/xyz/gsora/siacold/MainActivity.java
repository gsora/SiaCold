package xyz.gsora.siacold;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import siawallet.Wallet;
import xyz.gsora.siacold.ExplorerAPI.ExplorerAPI;
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
    private int howManyTimesIDidWrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        howManyTimesIDidWrong = 0;

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } catch (Exception e) {
            Log.d(TAG, "i crashed :v ->" + e.toString());
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Crypto.showAuthenticationScreen(this, (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE));

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

        fab.setOnClickListener(view -> {
            Crypto c = new Crypto(this, getApplicationContext(), null, null);
            c.tryDecrypt();
            Wallet w = new Wallet();
            try {
                w.setSeed(c.getDecryptedData());
                Realm r = Utils.getRealm();
                r.executeTransaction(realm -> {
                    long lastInsertedId = realm.where(Address.class).findAllSorted("id").last().getId();
                    realm.insertOrUpdate(new Address(w.getAddress(Utils.incrementSeedInt(this, "main")), lastInsertedId + 1));
                });

                RealmResults<Address> k = r.where(Address.class).findAllSorted("id");
                for (RealmObject l : k) {
                    Log.d("Realm objects", "onCreateView: ->" + l.toString());
                }

            } catch (NoDecryptedDataException e) {
                e.printStackTrace();
            }
        });

        test();
    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    public void test() {
        String addr = "c269433f56c29d752ce59895edce82fa992f9dcdc2734a8e6a9ee337b7e44de33c3afe193883";
        ExplorerAPI e = ExplorerAPI.getInstance();
        e.getUnlockHashInfo(addr)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(data -> {
                    Log.d(TAG, "reply -> " + data.body().getTotalcoins());
                });

        /*
        http://explore.sia.tech/js/hash.js -> search for "appendStat(table, 'Value', "
         */
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SiaCold.REQUEST_CODE:
                // Challenge completed, proceed with using cipher
                if (resultCode != RESULT_OK) {
                    howManyTimesIDidWrong++;
                    Crypto.showAuthenticationScreen(this, (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE));
                }
                break;
        }
    }

}

