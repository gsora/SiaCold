package xyz.gsora.siacold;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by gsora on 6/28/17.
 *
 * Utility class
 */
public class Utils {
    public static void toast(Activity activity, String message) {
        Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
