package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import models.User;
import view.subject.SubjectController;

/**
 * The main class for launching the JavaFX application as a student.
 *
 * @author irati
 */
public class SubjectMainStudent extends Application {
    /**
     * The main entry point for the JavaFX application.
     * Initializes the user, loads the subject view, and sets up the controller.
     * 
     * @param primaryStage The primary stage for the JavaFX application.
     * @throws Exception If an error occurs during the initialization of the JavaFX application.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        User user = new User();
        user.setId(2);
        user.setUser_type("Student");
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/subject/subject.fxml"));
        Parent root = (Parent) loader.load();
        SubjectController controller = (SubjectController) loader.getController();
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
