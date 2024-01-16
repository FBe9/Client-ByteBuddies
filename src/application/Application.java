package application;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import models.LevelType;
import models.Student;
import models.StudiesType;
import models.Teacher;
import models.User;
import view.*;
import view.exam.ExamWindowController;

/**
 * Main application class. Start the application.
 *
 * @author Nerea
 */
public class Application extends javafx.application.Application {

    /**
     * Default constructor for the class Application.
     */
    public Application() {
    }

    /**
     * Start method for the Application.
     *
     * @param stage The stage for the window.
     */
    @Override
    public void start(Stage stage) {
        Student student = new Student();
        student.setId("4");
        student.setName("Alex");
        student.setSurname("Salinero");
        student.setEmail("alexsalineronegro@gmail.com");
        student.setLevelType(LevelType.BEGGINER);
        
        Teacher teacher = new Teacher();
        teacher.setId("5");
        teacher.setName("Javi");
        teacher.setSurname("Teacher");
        teacher.setEmail("javiteacher@gmail.com");
        teacher.setQualifications("Certainly qualified");
        teacher.setStudiesType(StudiesType.MASTER);
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/exam/ExamWindow.fxml"));
            Parent root = (Parent) loader.load();
            ExamWindowController controller = (ExamWindowController) loader.getController();
            controller.setUser(student);
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
