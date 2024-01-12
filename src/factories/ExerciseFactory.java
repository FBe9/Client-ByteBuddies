package factories;

import interfaces.ExerciseInterface;
import services.ExerciseFacadeREST;

/**
 *
 * @author Leire
 */
public class ExerciseFactory {
    public static ExerciseInterface model;

    public static ExerciseInterface getModel() {
        if (model == null) {
            model = new ExerciseFacadeREST();
        }
        return model;
    }
}
