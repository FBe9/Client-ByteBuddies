/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.exercise;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Leire
 */
public class ExerciseController implements Initializable {

    @FXML
    private TableView tvExercise;
    @FXML
    private TableColumn tcUnit, tcNumber, tcDescription, tcLevelType, tcFile, tcFileSolution, tcDeadline, tcHours;
    @FXML
    private TextField tfSearch, tfNumber, tfDescription, tfFileSolution, tfFile, tfHours;
    @FXML
    private ComboBox cbUnitSearch, cbSearchType, cbUnitCreate, cbLevelTypeCreate;
    @FXML
    private DatePicker dpDeadline;
    @FXML
    private Button btmSearch, btmDelete, btmModify, btmCreate, btmPrint;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
