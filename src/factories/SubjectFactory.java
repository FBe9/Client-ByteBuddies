/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import implementation.SubjectManagerImplementation;
import interfaces.SubjectManager;

/**
 * Factory class for obtaining an instance of SubjectManager.
 *
 * @author irati
 */
public class SubjectFactory {

    /**
     * Singleton instance of SubjectManager.
     */
    public static SubjectManager model;

    /**
     * Retrieves an instance of SubjectManager. If the instance doesn't exist,
     * it creates a new instance of SubjectManagerImplementation.
     *
     * @return An instance of SubjectManager.
     */
    public static SubjectManager getModel() {
        if (model == null) {
            model = new SubjectManagerImplementation();
        }
        return model;
    }
}
