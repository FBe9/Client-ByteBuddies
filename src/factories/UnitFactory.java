/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import implementation.UnitManagerImplementation;
import interfaces.UnitInterface;

/**
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
