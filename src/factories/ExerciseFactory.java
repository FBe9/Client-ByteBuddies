package factories;

import implementation.ExerciseInterfaceImplementation;
import interfaces.ExerciseInterface;

/**
 * Factory class for obtaining an instance of ExerciseInterface.
 * 
 * @author Leire
 */
public class ExerciseFactory {
    public static ExerciseInterface model;

    public static ExerciseInterface getModel() {
        if (model == null) {
            model = new ExerciseInterfaceImplementation();
        }
        return model;
    }
}
