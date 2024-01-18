package exceptions;

/**
 * Exception if any error happens during data creation.
 * @author irati
 */
public class WrongNameFormatException extends Exception {
    /**
     * Creates a new instance without message.
     */
    public WrongNameFormatException() {
    }
    /**
     * Creates a new instance with message.
     */
    public WrongNameFormatException(String message) {
        super(message);
    }
    
}
