package exceptions;

/**
 * Exception if any error happens during data reading.
 * @author irati
 */
public class FindErrorException extends Exception {
     /**
     * Creates a new instance without message.
     */
    public FindErrorException() {
    }
    /**
     * Creates a new instance with message.
     */
    public FindErrorException(String message) {
        super(message);
    }
    
}
