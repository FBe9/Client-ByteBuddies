package factories;

import interfaces.ExerciseInterface;

/**
 *
 * @author Leire
 */
public class ExerciseFactory {

    /**
     *
     */
     public static ExerciseInterface model;

    public static ExerciseInterface getModel() {
        if (model == null) {
            model = new ExerciseRestful();
        }
        return model;
    }
}