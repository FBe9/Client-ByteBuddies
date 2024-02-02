package main;

import application.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import models.User;
import view.exercise.ExerciseController;

/**
 *
 * @author Leire
 */
public class ExerciseMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        User user = new User();
        user.setId(1);
        user.setUser_type("Teacher");
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/exercise/exercise.fxml"));
        Parent root = (Parent) loader.load();
        ExerciseController controller = (ExerciseController) loader.getController();
        controller.setStage(primaryStage);
        controller.initialize(root, user);
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
