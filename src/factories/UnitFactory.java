package factories;

import implementation.UnitManagerImplementation;
import interfaces.UnitInterface;

/**
 * This class is a factory that gets the model of the Unit interface
 * implementation.
 *
 * @author Nerea
 */
public class UnitFactory {

    public static UnitInterface model;

    public static UnitInterface getModel() {
        if (model == null) {
            model = new UnitManagerImplementation();
        }
        return model;
    }
}
