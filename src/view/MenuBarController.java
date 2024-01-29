/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import exceptions.ExerciseErrorException;
import exceptions.FindErrorException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.User;
import view.exam.*;
import view.exercise.*;
import view.subject.*;
import view.unit.*;

/**
 * FXML Controller class for MenuBar.fxml.
 *
 * @author Nerea
 * @author Irati
 */
public class MenuBarController {

    @FXML
    private HBox hBoxMenu;
    @FXML
    private MenuItem miSubjects;
    @FXML
    private MenuItem miExams;
    @FXML
    private MenuItem miUnits;
    @FXML
    private MenuItem miExercises;
    @FXML
    private MenuItem miHelpSubjects;
    @FXML
    private MenuItem miHelpExams;
    @FXML
    private MenuItem miHelpUnits;
    @FXML
    private MenuItem miHelpExercises;
    @FXML
    private MenuItem miCloseApp;
    @FXML
    private MenuItem miLogOut;

    private static Stage stageMenu;
    private static User loggedUser;

    private static final Logger LOGGER = Logger.getLogger("package view");

    /**
     * Initializes the controller class.
     */
    public void handleSubjectMenuItemAction(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/subject/subject.fxml"));
            Parent root = (Parent) loader.load();
            // Obtain the Sign In window controller
            SubjectController controller = (SubjectController) loader.getController();
            controller.setStage(stageMenu);
            controller.initStage(root, loggedUser);
        } catch (IOException ex) {
            Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleUnitMenuItemAction(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/unit/UnitWindow.fxml"));
            Parent root = (Parent) loader.load();
            // Obtain the Sign In window controller
            UnitWindowController controller = (UnitWindowController) loader.getController();
            controller.setStage(stageMenu);
            controller.initStage(root, loggedUser);
        } catch (IOException ex) {
            Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleExamMenuItemAction(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/exam/ExamWindow.fxml"));
            Parent root = (Parent) loader.load();
            // Obtain the Sign In window controller
            ExamWindowController controller = (ExamWindowController) loader.getController();
            controller.setStage(stageMenu);
            controller.initStage(root);
            controller.setUser(loggedUser);
        } catch (IOException ex) {
            Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleExerciseMenuItemAction(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/exercise/exercise.fxml"));
            Parent root = (Parent) loader.load();
            // Obtain the Sign In window controller
            ExerciseController controller = (ExerciseController) loader.getController();
            controller.setStage(stageMenu);
            controller.initialize(root, loggedUser);
        } catch (ExerciseErrorException | FindErrorException | IOException ex) {
            Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleSubjectHelpMenuItemAction(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/subject/HelpSubject.fxml"));
            Parent root = (Parent) loader.load();
            // Obtain the Sign In window controller
            //HelpExamController controller = (HelpExamController) loader.getController();
            //controller.initialize(root);
        } catch (IOException ex) {
            Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleUnitHelpMenuItemAction(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/unit/HelpUnit.fxml"));
            Parent root = (Parent) loader.load();
            // Obtain the Sign In window controller
            //HelpUnitController controller = (HelpUnitController) loader.getController();
            //controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleExamHelpMenuItemAction(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/exam/HelpExam.fxml"));
            Parent root = (Parent) loader.load();
            // Obtain the Sign In window controller
            HelpExamController controller = (HelpExamController) loader.getController();
            controller.initialize(root);
        } catch (IOException ex) {
            Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleExerciseHelpMenuItemAction(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/exercise/HelpExercise.fxml"));
            Parent root = (Parent) loader.load();
            // Obtain the Sign In window controller
            //HelpExerciseController controller = (HelpExerciseController) loader.getController();
            //controller.initialize(root);
        } catch (IOException ex) {
            Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleCloseApplicationMenuItemAction(Event event) {
        try {
            //Ask user for confirmation on exit
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "¿Are you sure you want to exit the application?",
                    ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            //If OK to exit
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Platform.exit();
            } else {
                event.consume();
            }
        } catch (Exception e) {
            String errorMsg = "Error exiting application:\n" + e.getMessage();
            Alert alert = new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
            alert.showAndWait();
            LOGGER.log(Level.SEVERE, errorMsg);
        }
    }

    public void handleLogOutMenuItemAction(Event event) {
      /*try {
            //Ask user for confirmation on exit
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, loggedUser.getName()+", are you sure that you want to log out?");
            Optional<ButtonType> action = alert.showAndWait();

            //If OK to exit 
            if (action.isPresent() && action.get() == ButtonType.OK) {
                stageMenu.close();

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/SignInWindow.fxml"));
                    Parent root = (Parent) loader.load();
                    SignInWindowController controller = (SignInWindowController) loader.getController();
                    controller.setStage(stageMenu);

                    controller.initStage(root);
                } catch (IOException ex) {
                    Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                event.consume();
            }
        } catch (Exception e) {
            String errorMsg = "Error logging out from the application:\n" + e.getMessage();
            Alert alert = new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
            alert.showAndWait();
            LOGGER.log(Level.SEVERE, errorMsg);
        }*/
    }

    public static void setUser(User user) {
        loggedUser = user;
    }

    public static void setStage(Stage stage) {
        stageMenu = stage;
    }
}