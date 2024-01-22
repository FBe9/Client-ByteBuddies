package application;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import models.*;
import models.User;
import view.unit.UnitWindowController;

/**
 *
 * @author Nerea
 */
public class Application extends javafx.application.Application {
    
    @Override
    public void start(Stage stage) {
       try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/unit/UnitWindow.fxml"));
            Parent root = (Parent) loader.load();
            // Obtain the Sign In window controller
            UnitWindowController controller = (UnitWindowController) loader.getController();
            User user = new Teacher();
            user.setId(1);
            user.setUser_type("Teacher");
            controller.setStage(stage);
            controller.initStage(root,user);
        } catch (IOException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
