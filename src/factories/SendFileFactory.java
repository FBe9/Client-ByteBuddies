package factories;

import implementation.SendFileInterfaceImplementation;
import interfaces.SendFileInterface;

/**
 * Factory class for obtaining an instance of SendFileInterface.
 *
 * @author Alex
 */
public class SendFileFactory {

    /**
     * The instance of SendFileInterface.
     */
    public static SendFileInterface model;

    /**
     * When there isn't an instance of SendFileInterface, it creates a new one.
     *
     * @return The instance of ExamInterface.
     */
    public static SendFileInterface getModel() {
        if (model == null) {
            model = new SendFileInterfaceImplementation();
        }
        return model;
    }
}
