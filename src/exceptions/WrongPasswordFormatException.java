package exceptions;

/**
 * Class for Wrong password format exception.
 *
 * @author Nerea
 */
public class WrongPasswordFormatException extends Exception {

    /**
     * Creates a new instance of <code>WrongPasswordFormatException</code>
     * without detail message.
     */
    public WrongPasswordFormatException() {
    }

    /**
     * Constructs an instance of <code>WrongPasswordFormatException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public WrongPasswordFormatException(String msg) {
        super(msg);
    }
}
