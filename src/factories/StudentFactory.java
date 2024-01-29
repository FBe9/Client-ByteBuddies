package factories;

import implementation.StudentInterfaceImplementation;
import interfaces.StudentInterface;

/**
 *  Factory class to obtain the implementation of a Student.
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
