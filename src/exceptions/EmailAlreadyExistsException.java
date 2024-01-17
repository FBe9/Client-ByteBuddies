package exceptions;

/**
 * Exception if any error happens during data creation.
 * @author irati
 */
public class EmailAlreadyExistsException extends Exception {
    /**
     * Creates a new instance without message.
     */
    public EmailAlreadyExistsException() {
    }
    /**
     * Creates a new instance with message.
     */
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
    
}
