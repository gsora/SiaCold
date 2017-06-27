package xyz.gsora.siacold;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.security.keystore.UserNotAuthenticatedException;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Arrays;

/**
 * Created by gsora on 6/27/17.
 *
 * Wrapper class for encryption/decryption, and pattern/PIN/fingerprint unlock.
 */
public class Crypto {

    private static String TAG = Crypto.class.getSimpleName();

    private KeyguardManager keyguard;
    private Activity activity;
    private Context ctx;
    private Fragment f;

    public Crypto(Activity activity, Context ctx, Fragment f) {
        this.ctx = ctx;
        this.activity = activity;
        this.f = f;
        keyguard = (KeyguardManager) activity.getSystemService(Context.KEYGUARD_SERVICE);

    }

    public void showAuthenticationScreen(Fragment fragment) {
        // Create the Confirm Credentials screen. You can customize the title and description. Or
        // we will provide a generic one for you if you leave it null
        Intent intent = keyguard.createConfirmDeviceCredentialIntent(null, null);
        if (intent != null) {
            fragment.startActivityForResult(intent, SiaCold.REQUEST_CODE);
        }
    }

    public void createKey() {
        // Generate a key to decrypt payment credentials, tokens, etc.
        // This will most likely be a registration step for the user when they are setting up your app.
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            KeyGenerator keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            // Set the alias of the entry in Android KeyStore where the key will appear
            // and the constrains (purposes) in the constructor of the Builder
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                keyGenerator.init(new KeyGenParameterSpec.Builder("seed",
                        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setUserAuthenticationRequired(true)
                        // Require that the user has unlocked in the last 30 seconds
                        .setUserAuthenticationValidityDurationSeconds(SiaCold.AUTH_DURATION)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .build());
            }
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException | NoSuchProviderException
                | InvalidAlgorithmParameterException | KeyStoreException
                | CertificateException | IOException e) {
            throw new RuntimeException("Failed to create a symmetric key", e);
        }
    }

    /**
     * Tries to encrypt some data with the generated key in {@link #createKey} which is
     * only works if the user has just authenticated via device credentials.
     */
    public boolean tryEncrypt(byte[] data) {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            SecretKey secretKey = (SecretKey) keyStore.getKey(SiaCold.KEY_NAME, null);
            Cipher cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);

            // Try encrypting something, it will only work if the user authenticated within
            // the last AUTHENTICATION_DURATION_SECONDS seconds.
            byte[] iv = cipher.getIV();
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

            Log.d(TAG, "tryEncrypt: IV " + iv);
            SharedPreferences.Editor editor = activity.getSharedPreferences("iv", Activity.MODE_PRIVATE).edit();
            editor.putString("encryptionIv", Base64.encodeToString(iv, Base64.NO_PADDING));
            editor.apply();

            cipher.doFinal(data);

            // If the user has recently authenticated, you will reach here.
            return true;
        } catch (UserNotAuthenticatedException e) {
            // User is not authenticated, let's authenticate with device credentials.
            Log.d(TAG, "tryEncrypt: " + e.toString());
            showAuthenticationScreen(f);
            return false;
        } catch (KeyPermanentlyInvalidatedException e) {
            Log.d(TAG, "tryEncrypt: " + e.toString());
            // This happens if the lock screen has been disabled or reset after the key was
            // generated after the key was generated.
            Toast.makeText(activity, "Keys are invalidated after created. Retry the purchase\n"
                            + e.getMessage(),
                    Toast.LENGTH_LONG).show();
            return false;
        } catch (BadPaddingException | IllegalBlockSizeException | KeyStoreException
                | CertificateException | UnrecoverableKeyException | IOException
                | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            Log.d(TAG, "tryEncrypt: " + e.toString());
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean tryDecrypt() {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            SecretKey secretKey = (SecretKey) keyStore.getKey(SiaCold.KEY_NAME, null);
            Cipher cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);

            // Try encrypting something, it will only work if the user authenticated within
            // the last AUTHENTICATION_DURATION_SECONDS seconds.
            SharedPreferences s = activity.getSharedPreferences("iv", Activity.MODE_PRIVATE);
            String strIv = s.getString("encryptionIv", null);

            Log.d("HEH", "tryDecrypt: IV " + Arrays.toString(Base64.decode(strIv, Base64.NO_PADDING)));

            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(Base64.decode(strIv, Base64.NO_PADDING)));
            byte[] seed = cipher.doFinal();

            Log.d("HEH", "tryDecrypt: " + String.valueOf(seed));

            // If the user has recently authenticated, you will reach here.
            return true;
        } catch (UserNotAuthenticatedException e) {
            // User is not authenticated, let's authenticate with device credentials.
            showAuthenticationScreen(f);
            return false;
        } catch (KeyPermanentlyInvalidatedException e) {
            // This happens if the lock screen has been disabled or reset after the key was
            // generated after the key was generated.
            Toast.makeText(ctx, "Keys are invalidated after created. Retry the purchase\n"
                            + e.getMessage(),
                    Toast.LENGTH_LONG).show();
            return false;
        } catch (BadPaddingException | IllegalBlockSizeException | KeyStoreException
                | CertificateException | UnrecoverableKeyException | IOException
                | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            Log.d("HEHE", "tryDecrypt: " + e.toString());
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return true;
    }
}
