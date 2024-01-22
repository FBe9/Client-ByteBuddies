/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import implementation.ExamInterfaceImplementation;
import interfaces.ExamInterface;

/**
 *
 * @author Alex
 */
public class ExamFactory {
    
    public static ExamInterface model;
    
    public static ExamInterface getModel(){
        if(model == null){
            model = new ExamInterfaceImplementation();
        }
        return model;
    }
}
