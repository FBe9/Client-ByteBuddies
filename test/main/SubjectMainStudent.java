package main;

import application.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import models.User;
import view.subject.SubjectController;

/**
 *
 * @author irati
 */
public class SubjectMainStudent extends Application {

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
