package xyz.gsora.siacold.General;

import android.app.Application;
import io.realm.Realm;

/**
 * Created by gsora on 27/06/17.
 * <p>
 * SiaCold application class.
 */
public class SiaCold extends Application {

    public final static String KEY_NAME = "seed";
    public final static Integer AUTH_DURATION = 120;
    public final static int REQUEST_CODE = 5263;

    public final static String BTC_ADDRESS = "1EQ6QybdTnwSQjyHga3Boct44f2tfieBFe";
    public final static String ETH_ADDRESS = "0x33695F0404F7C2C059D29dF6720d7D5d5f9f1e70";
    public final static String SIA_ADDRESS = "c269433f56c29d752ce59895edce82fa992f9dcdc2734a8e6a9ee337b7e44de33c3afe193883";

    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
    }
}
