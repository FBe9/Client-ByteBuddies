/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import implementation.SendFileInterfaceImplementation;
import interfaces.SendFileInterface;

/**
 *
 * @author Alex
 */
public class SendFileFactory {
    
    public static SendFileInterface model;
    
    
    public static SendFileInterface getModel() {
        if (model == null) {
            model = new SendFileInterfaceImplementation();
        }
        return model;
    }
}
