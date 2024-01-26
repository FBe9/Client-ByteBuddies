/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
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
