package exceptions;

/**
 * Exception if any error happens during data creation.
 * @author irati
 */
public class CreateErrorException extends Exception {
    /**
     * Creates a new instance without message.
     */
    public CreateErrorException() {
    }
    /**
     * Creates a new instance with message.
     */
    public CreateErrorException(String message) {
        super(message);
    }
    
}
