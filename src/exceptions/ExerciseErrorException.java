/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
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
