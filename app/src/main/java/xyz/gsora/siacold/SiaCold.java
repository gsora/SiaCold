package xyz.gsora.siacold;

import android.app.Application;

/**
 * Created by gsora on 27/06/17.
 * <p>
 * SiaCold application class.
 */
public class SiaCold extends Application {

    public final static String KEY_NAME = "seed";
    public final static Integer AUTH_DURATION = 2;
    public final static int REQUEST_CODE = 5263;

    public void onCreate() {
        super.onCreate();
    }
}
