/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Teacher;
import models.User;
import view.subject.SubjectController;
import view.unit.UnitWindowController;

/**
 * FXML Controller class
 *
 * @author 2dam
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

    private Stage stage;
    private User loggedUser;

    /**
     * Initializes the controller class.
     *
     * @param loggedUser
     */
    public void initStage(User loggedUser) {
        this.loggedUser = loggedUser;

    }

    public void handleSubjectMenuItemAction(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/unit/subject.fxml"));
            Parent root = (Parent) loader.load();
            // Obtain the Sign In window controller
            SubjectController controller = (SubjectController) loader.getController();
            controller.setStage(stage);
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
            User user = new Teacher();
            user.setId(1);
            user.setUser_type("Teacher");
            controller.setStage(stage);
            controller.initStage(root, loggedUser);
        } catch (IOException ex) {
            Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleExamMenuItemAction(Event event) {

    }

    public void handleExerciseMenuItemAction(Event event) {

    }

    public void handleSubjectHelpMenuItemAction(Event event) {

    }

    public void handleUnitHelpMenuItemAction(Event event) {

    }

    public void handleExamHelpMenuItemAction(Event event) {

    }

    public void handleExerciseHelpMenuItemAction(Event event) {

    }

    public void handleCloseApplicationMenuItemAction(Event event) {

    }

    public void handleLogOutMenuItemAction(Event event) {

    }

}
