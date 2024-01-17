package exceptions;

/**
 * Class for Wrong phone number format exception.
 *
 * @author Nerea
 */
public class WrongMobileFormatException extends Exception {

    /**
     * Creates a new instance of <code>WrongMobileFormatException</code> without
     * detail message.
     */
    public WrongMobileFormatException() {
    }

    /**
     * Constructs an instance of <code>WrongMobileFormatException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public WrongMobileFormatException(String msg) {
        super(msg);
    }
}
