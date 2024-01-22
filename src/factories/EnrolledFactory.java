/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import implementation.EnrolledInterfaceImplementation;
import interfaces.EnrolledInterface;

/**
 *
 * @author irati
 */
public class EnrolledFactory {
    public static EnrolledInterface model;
    
    public static EnrolledInterface getModel() {
        if (model == null) {
            model = new EnrolledInterfaceImplementation();
        }
        return model;
    }
}
