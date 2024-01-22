/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import implementation.StudentInterfaceImplementation;
import interfaces.StudentInterface;

/**
 *
 * @author irati
 */
public class StudentFactory {

    public static StudentInterface model;

    /**
     * Retrieves an instance of UserInterface. If the instance doesn't exist, it
     * creates a new instance of UserInterfaceImplementation.
     *
     * @return An instance of UserInterface.
     */
    public static StudentInterface getModel() {
        if (model == null) {
            model = new StudentInterfaceImplementation();
        }
        return model;
    }
}
