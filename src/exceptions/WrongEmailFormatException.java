package exceptions;

/**
 * Class for Wrong email format exception.
 *
 * @author Nerea
 */
public class WrongEmailFormatException extends Exception {

    /**
     * Creates a new instance of <code>WrongEmailFormatException</code> without
     * detail message.
     */
    public WrongEmailFormatException() {
    }

    /**
     * Constructs an instance of <code>WrongEmailFormatException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public WrongEmailFormatException(String msg) {
        super(msg);
    }
}
