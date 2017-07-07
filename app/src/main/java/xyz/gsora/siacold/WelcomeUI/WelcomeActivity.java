package xyz.gsora.siacold.WelcomeUI;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.gsora.siacold.General.Utils;
import xyz.gsora.siacold.MainActivity;
import xyz.gsora.siacold.R;

public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.tutorialViewPager)
    NonSwipeableViewPager tutorialViewPager;

    @BindView(R.id.tabDots)
    TabLayout tabDots;

    private String TAG = "Welcome";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        if (setupDone()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        WelcomeUIAdapter adapter = new WelcomeUIAdapter(getSupportFragmentManager());
        tutorialViewPager.setAdapter(adapter);
        tutorialViewPager.setSwipeable(true);

        tabDots.setupWithViewPager(tutorialViewPager, true);
        checkIfSecureAndUsable();
        setupViewPagerListener();
    }

    public void checkIfSecureAndUsable() {
        KeyguardManager k = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!k.isDeviceSecure()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Cannot proceed");
                builder.setMessage("The device is not secure enough to use SiaCold.\n\nTo use SiaCold some sort of" +
                        "device protection (PIN, password, pattern, fingerprint) must be enabled.");
                builder.setNegativeButton("Exit SiaCold", (dialog, id) -> finish());

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

    void setupViewPagerListener() {
        // Attach the page change listener inside the activity
        tutorialViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    tutorialViewPager.setSwipeable(false);
                }
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });
    }

    void moveToNextPage() {
        tutorialViewPager.setCurrentItem(tutorialViewPager.getCurrentItem() + 1);
    }

    private boolean setupDone() {
        return Utils.getSharedPreferences(this, "main").getBoolean("setupDone", false);
    }

}
