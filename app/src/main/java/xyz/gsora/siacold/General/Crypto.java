package xyz.gsora.siacold.General;

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
import android.util.Log;
import android.widget.Toast;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.InvalidParameterSpecException;

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
    private byte[] encryptionData;
    private String decryptedData;

    public Crypto(Activity activity, Context ctx, Fragment f, byte[] data) {
        this.ctx = ctx;
        this.activity = activity;
        this.f = f;
        keyguard = (KeyguardManager) activity.getSystemService(Context.KEYGUARD_SERVICE);
        encryptionData = data;
    }

    public static void showAuthenticationScreen(Activity activity, KeyguardManager keyguard) {
        // Create the Confirm Credentials screen. You can customize the title and description. Or
        // we will provide a generic one for you if you leave it null
        Intent intent = keyguard.createConfirmDeviceCredentialIntent(null, null);
        if (intent != null) {
            activity.startActivityForResult(intent, SiaCold.REQUEST_CODE);
        }
    }

    public void showAuthenticationScreen(Fragment fragment) {
        // Create the Confirm Credentials screen. You can customize the title and description. Or
        // we will provide a generic one for you if you leave it null
        Intent intent = keyguard.createConfirmDeviceCredentialIntent(null, null);
        if (intent != null) {
            fragment.startActivityForResult(intent, SiaCold.REQUEST_CODE);
        }
    }

    private SecretKey createKey() {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            KeyGenerator keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

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
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException | NoSuchProviderException
                | InvalidAlgorithmParameterException | KeyStoreException
                | CertificateException | IOException e) {
            throw new RuntimeException("Failed to create a symmetric key", e);
        }
    }

    public boolean tryEncrypt() {
        try {
            SecretKey secretKey = createKey(); // create a secret key
            Cipher cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encData = cipher.doFinal(encryptionData);
            byte[] iv = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();

            SharedPreferences.Editor editor = Utils.getSharedPreferences(activity, "encryption").edit();
            editor.putString("encryptionIv", Utils.base64Encode(iv));
            editor.putString("encryptedSeed", Utils.base64Encode(encData));
            editor.apply();

            return true;
        } catch (UserNotAuthenticatedException e) {
            // User is not authenticated, let's authenticate with device credentials.
            showAuthenticationScreen(f);
            return false;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | InvalidParameterSpecException e) {
            Log.d(TAG, "ABOMINAL ERROR OCCURRED: " + e.toString());
            throw new RuntimeException(e);
        }
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
            SharedPreferences s = Utils.getSharedPreferences(activity, "encryption");
            String iv = s.getString("encryptionIv", null);
            String encSeed = s.getString("encryptedSeed", null);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(Utils.base64Decode(iv)));
            byte[] seed = cipher.doFinal(Utils.base64Decode(encSeed));

            decryptedData = new String(seed, StandardCharsets.UTF_8);
            return true;
        } catch (UserNotAuthenticatedException e) {
            // User is not authenticated, let's authenticate with device credentials.
            showAuthenticationScreen(f);
            return false;
        } catch (KeyPermanentlyInvalidatedException e) {
            // This happens if the lock screen has been disabled or reset after the key was
            // generated after the key was generated.
            Toast.makeText(ctx, "Keys are invalidated after created.\n"
                            + e.getMessage(),
                    Toast.LENGTH_LONG).show();
            return false;
        } catch (BadPaddingException | IllegalBlockSizeException | KeyStoreException
                | CertificateException | UnrecoverableKeyException | IOException
                | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return true;
    }

    public String getDecryptedData() throws NoDecryptedDataException {
        if (decryptedData == null) {
            throw new NoDecryptedDataException("no decrypted data available, have you called tryDecrypt()?");
        }

        return decryptedData;
    }
}
