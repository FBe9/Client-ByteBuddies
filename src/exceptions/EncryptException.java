package exceptions;

/**
 * Exception if any error happens during data creation.
 * @author irati
 */
public class EncryptException extends Exception {
    /**
     * Creates a new instance without message.
     */
    public EncryptException() {
    }
    /**
     * Creates a new instance with message.
     * 
     * @param message The message of the error.
     */
    public EncryptException(String message) {
        super(message);
    }
    
}
