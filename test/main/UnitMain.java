package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import models.User;
import view.unit.UnitWindowController;

/**
 *
 * @author Nerea
 */
public class UnitMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        User user = new User();
        user.setId(1);
        user.setUser_type("Teacher");
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/unit/UnitWindow.fxml"));
        Parent root = (Parent) loader.load();
        UnitWindowController controller = (UnitWindowController) loader.getController();
        controller.setStage(primaryStage);
        controller.initStage(root, user);
    }

    /**
     * Entry point for the Java application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
