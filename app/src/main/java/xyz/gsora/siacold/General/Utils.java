package xyz.gsora.siacold.General;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.Base64;
import android.widget.Toast;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import xyz.gsora.siacold.R;

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

    public static Realm getRealm() {
        return Realm.getInstance(
                new RealmConfiguration.Builder()
                        .deleteRealmIfMigrationNeeded()
                        .build());
    }

    public static void saveToPrefs(Context ctx, String key, String value) {
        SharedPreferences s = getSharedPreferences(ctx, "main");
        SharedPreferences.Editor se = s.edit();
        se.putString(key, value);
        se.apply();
    }

    public static void shareAddress(Address a, Context c) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = a.getAddress();
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "This is my Siacoin address");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "This is my Siacoin address: " + shareBody);
        c.startActivity(Intent.createChooser(sharingIntent, "Share your Siacoin address via"));
    }

    public static Long incrementSeedInt(Context ctx, String name) {
        SharedPreferences s = getSharedPreferences(ctx, name);
        Long oldInt = s.getLong("seedInt", 0);

        SharedPreferences.Editor se = s.edit();
        se.putLong("seedInt", oldInt + 1);
        se.apply();

        return oldInt + 1;
    }

    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

    public static int getTabsHeight(Context context) {
        return (int) context.getResources().getDimension(R.dimen.tabsHeight);
    }
}
