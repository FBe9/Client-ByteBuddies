package exceptions;

/**
 * Exception if any error happens during data creation.
 *
 * @author irati
 */
public class WrongDateFormatException extends Exception {

    /**
     * Creates a new instance without message.
     */
    public WrongDateFormatException() {
    }

    /**
     * Creates a new instance with message.
     *
     * @param message The exception message.
     */
    public WrongDateFormatException(String message) {
        super(message);
    }

}
