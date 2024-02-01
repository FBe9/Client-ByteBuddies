package factories;

import implementation.ExerciseInterfaceImplementation;
import interfaces.ExerciseInterface;

/**
 * Factory class for obtaining an instance of ExerciseInterface.
 *
 * @author Leire
 */
public class ExerciseFactory {

    /**
     * The instance of ExerciseInterface.
     */
    public static ExerciseInterface model;

    /**
     * When there isn't an instance of ExerceiseInterface, it creates a new one.
     * 
     * @return The instance of ExerciseInterface.
     */
    public static ExerciseInterface getModel() {
        if (model == null) {
            model = new ExerciseInterfaceImplementation();
        }
        return model;
    }
}
