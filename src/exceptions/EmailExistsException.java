package exceptions;

/**
 * Exception if any error happens during data creation.
 * @author irati
 */
public class EmailExistsException extends Exception {
    /**
     * Creates a new instance without message.
     */
    public EmailExistsException() {
    }
    /**
     * Creates a new instance with message.
     */
    public EmailExistsException(String message) {
        super(message);
    }
    
}
