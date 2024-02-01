package factories;

import implementation.TeacherInterfaceImplementation;
import interfaces.TeacherInterface;

/**
 * Factory class to obtain the implementation of a Teacher.
 *
 * @author irati
 */
public class TeacherFactory {

    /**
     * The instance of UserInterface.
     */
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
