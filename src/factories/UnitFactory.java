/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import interfaces.UnitInterface;
import services.UnitFacadeREST;

/**
 *
 * @author 2dam
 */
public class UnitFactory {
    public static UnitInterface model;

    public static UnitInterface getModel() {
        if (model == null) {
            model = new UnitFacadeREST();
        }
        return model;
    }
}
