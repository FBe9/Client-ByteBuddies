/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.exercise;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Exercise;

/**
 * FXML Controller class
 *
 * @author Leire
 */
public class ExerciseController {
    
    private static final Logger LOGGER = Logger.getLogger("view");
    private Stage stage;
    private User user;
    private ObservableList<Exercise> exerciseData;
    //private Exercise byID;
    //private List<MentalDisease> byName = new ArrayList<>();
    //iniciar factoria
    ExerciseFactory exerciseFactory = new ExerciseFactory();
    //obtener mediante la factoria la interface
    ExerciseInterface exerciseInterface = exerciseFactory.getModel();

    @FXML
    private TableView tvExercise;
    @FXML
    private TableColumn tcUnit, tcNumber, tcDescription, tcLevelType, tcFile, tcFileSolution, tcDeadline, tcHours;
    @FXML
    private TextField tfSearch, tfNumber, tfDescription, tfFileSolution, tfFile, tfHours;
    @FXML
    private ComboBox<?> cbUnit, cbSearch, cbUnitCreate, cbLevelType;
    @FXML
    private DatePicker dpDeadline;
    @FXML
    private Button btmDelete, btmModify, btmCreate, btmDelete1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
