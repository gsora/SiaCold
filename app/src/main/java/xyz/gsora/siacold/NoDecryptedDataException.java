package xyz.gsora.siacold;

/**
 * Created by gsora on 29/06/17.
 * <p>
 * Exception thrown when no decrypted data is available.
 */
public class NoDecryptedDataException extends Exception {

    public NoDecryptedDataException(String message) {
        super(message);
    }
}
