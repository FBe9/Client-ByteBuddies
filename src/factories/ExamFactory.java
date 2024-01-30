package factories;

import implementation.ExamInterfaceImplementation;
import interfaces.ExamInterface;

/**
 * Factory class for obtaining an instance of ExamInterface.
 *
 * @author Alex
 */
public class ExamFactory {

    /**
     * The instance of ExamInterface.
     */
    public static ExamInterface model;

    /**
     * When there isn't an instance of ExamInterface, it creates a new one.
     *
     * @return The instance of ExamInterface.
     */
    public static ExamInterface getModel() {
        if (model == null) {
            model = new ExamInterfaceImplementation();
        }
        return model;
    }
}
