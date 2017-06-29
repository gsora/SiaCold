package xyz.gsora.siacold;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
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

    public static SharedPreferences getSharedPreferences(Context ctx, String name) {
        return ctx.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static byte[] base64Decode(String s) {
        return Base64.decode(s, Base64.DEFAULT);
    }

    public static String base64Encode(byte[] data) {
        return Base64.encodeToString(data, Base64.DEFAULT);
    }
}
