package exceptions;

/**
 * Exception if any error happens during data creation.
 * @author irati
 */
public class SubjectNameAlreadyExistsException extends Exception {
    /**
     * Creates a new instance without message.
     */
    public SubjectNameAlreadyExistsException() {
    }
    /**
     * Creates a new instance with message.
     */
    public SubjectNameAlreadyExistsException(String message) {
        super(message);
    }
    
}
