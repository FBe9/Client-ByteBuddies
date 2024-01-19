package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import models.User;
import view.exercise.ExerciseController;

/**
 *
 * @author irati
 */
public class ApplicationByteBuddies extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        User user = new User();
        user.setId(2);
        user.setUser_type("Student");
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
