package main;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import models.User;
import view.exam.ExamWindowController;

/**
 *
 * @author Alex
 */
public class ExamMainStudent extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        User user = new User();
        user.setId(2);
        user.setUser_type("Student");
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/exam/ExamWindow.fxml"));
        Parent root = (Parent) loader.load();
        ExamWindowController controller = (ExamWindowController) loader.getController();
        controller.setStage(primaryStage);
        controller.setUser(user);
        controller.initStage(root);
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
