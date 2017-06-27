package xyz.gsora.siacold.WelcomeUI;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.gsora.siacold.R;

public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.tutorialViewPager)
    ViewPager tutorialViewPager;

    @BindView(R.id.tabDots)
    TabLayout tabDots;

    private String TAG = "Welcome";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        WelcomeUIAdapter adapter = new WelcomeUIAdapter(getSupportFragmentManager());
        tutorialViewPager.setAdapter(adapter);

        tabDots.setupWithViewPager(tutorialViewPager, true);
        checkIfSecureAndUsable();
    }

    public void checkIfSecureAndUsable() {
        KeyguardManager k = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!k.isDeviceSecure()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Cannot proceed");
                builder.setMessage("The device is not secure enough to use SiaCold.\n\nTo use SiaCold some sort of" +
                        "device protection (PIN, password, pattern, fingerprint) must be enabled.");
                builder.setNegativeButton("Exit SiaCold", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

}
