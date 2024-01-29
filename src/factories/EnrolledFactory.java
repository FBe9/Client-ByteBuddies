package factories;

import implementation.EnrolledInterfaceImplementation;
import interfaces.EnrolledInterface;

/**
 * Factory class to obtain the implementation of a Enrolled.
 *
 * @author irati
 */
public class EnrolledFactory {

    /**
     * The EnrolledInterface instance created or retrieved by the factory.
     */
    public static EnrolledInterface model;

    /**
     * Retrieves or creates an EnrolledInterface instance.
     *
     * @return The EnrolledInterface instance.
     */
    public static EnrolledInterface getModel() {
        if (model == null) {
            model = new EnrolledInterfaceImplementation();
        }
        return model;
    }
}
