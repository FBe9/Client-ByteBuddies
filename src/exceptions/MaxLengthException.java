package exceptions;

/**
 * Exception if any error happens during data creation.
 *
 * @author irati
 */
public class MaxLengthException extends Exception {

    /**
     * Creates a new instance without message.
     */
    public MaxLengthException() {
    }

    /**
     * Creates a new instance with message.
     *
     * @param message The exception message.
     */
    public MaxLengthException(String message) {
        super(message);
    }

}
