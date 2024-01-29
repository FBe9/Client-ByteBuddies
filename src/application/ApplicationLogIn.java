/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import view.login.SignInWindowController;

/**
 *
 * @author irati
 */
public class ApplicationLogIn extends javafx.application.Application {

    /**
     * Default constructor for the class Application.
     */
    public ApplicationLogIn() {
    }

    /**
     * Start method for the Application.
     *
     * @param stage The stage for the window.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/login/SignInWindow.fxml"));
            Parent root = (Parent) loader.load();
            SignInWindowController controller = (SignInWindowController) loader.getController();
            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Main application method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}