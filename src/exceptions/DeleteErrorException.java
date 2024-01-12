package exceptions;

/**
 * Exception if any error happens during delete.
 * @author irati
 */
public class DeleteErrorException extends Exception {
     /**
     * Creates a new instance without message.
     */
    public DeleteErrorException() {
    }
    /**
     * Creates a new instance with message.
     */
    public DeleteErrorException(String message) {
        super(message);
    }
    
}
