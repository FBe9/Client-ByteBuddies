package factories;

import implementation.UserInterfaceImplementation;
import interfaces.UserInterface;

/**
 * Factory class for obtaining an instance of UserInterface.
 *
 * @author irati
 */
public class UserFactory {

    /**
     * Singleton instance of UserInterface.
     */
    public static UserInterface model;

    /**
     * Retrieves an instance of UserInterface. If the instance doesn't exist, it
     * creates a new instance of UserInterfaceImplementation.
     *
     * @return An instance of UserInterface.
     */
    public static UserInterface getModel() {
        if (model == null) {
            model = new UserInterfaceImplementation();
        }
        return model;
    }
}
