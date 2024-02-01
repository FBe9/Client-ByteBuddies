package exceptions;

/**
 * Exception if any error happens during data creation.
 *
 * @author irati
 */
public class WrongNumberFormatException extends Exception {

    /**
     * Creates a new instance without message.
     */
    public WrongNumberFormatException() {
    }

    /**
     * Creates a new instance with message.
     *
     * @param message The exception message.
     */
    public WrongNumberFormatException(String message) {
        super(message);
    }

}
