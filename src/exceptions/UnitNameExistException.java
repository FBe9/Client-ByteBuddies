package exceptions;

/**
 * Exception if any error happens during data creation.
 * 
 * @author Usuario
 */
public class UnitNameExistException extends Exception {

    /**
     * Creates a new instance of <code>UnitNameExistException</code> without
     * detail message.
     */
    public UnitNameExistException() {
    }

    /**
     * Constructs an instance of <code>UnitNameExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UnitNameExistException(String msg) {
        super(msg);
    }
}
