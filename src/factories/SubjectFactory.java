/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import implementation.SubjectManagerImplementation;
import services.SubjectRESTClient;
import interfaces.SubjectManager;

/**
 *
 * @author irati
 */
public class SubjectFactory {

    public static SubjectManager model;

    public static SubjectManager getModel() {
        if (model == null) {
            model = new SubjectManagerImplementation();
        }
        return model;
    }
}
