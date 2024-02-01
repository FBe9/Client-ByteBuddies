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

    /**
     * The instance of UnitInterface.
     */
    public static UnitInterface model;

    /**
     * When there isn't an instance of UnitInterface, it creates a new one.
     * 
     * @return The instance of UnitInterface.
     */
    public static UnitInterface getModel() {
        if (model == null) {
            model = new UnitManagerImplementation();
        }
        return model;
    }
}
