/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import implementation.TeacherInterfaceImplementation;
import interfaces.TeacherInterface;

/**
 *
 * @author irati
 */
public class TeacherFactory {

    public static TeacherInterface model;

    /**
     * Retrieves an instance of UserInterface. If the instance doesn't exist, it
     * creates a new instance of UserInterfaceImplementation.
     *
     * @return An instance of UserInterface.
     */
    public static TeacherInterface getModel() {
        if (model == null) {
            model = new TeacherInterfaceImplementation();
        }
        return model;
    }
}
