package exceptions;

/**
 * Exception if any error happens during data creation.
 *
 * @author 2dam
 */
public class ExerciseErrorException extends Exception {

    /**
     * Creates a new instance of <code>ExerciseErrorException</code> without
     * detail message.
     */
    public ExerciseErrorException() {
    }

    /**
     * Constructs an instance of <code>ExerciseErrorException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ExerciseErrorException(String msg) {
        super(msg);
    }
}
