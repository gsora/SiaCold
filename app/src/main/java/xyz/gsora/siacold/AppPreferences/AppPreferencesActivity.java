package xyz.gsora.siacold.AppPreferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import xyz.gsora.siacold.R;

public class AppPreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_preferences);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.prefFragment, AppPreferencesFragment.newInstance()).commit();

    }
}
