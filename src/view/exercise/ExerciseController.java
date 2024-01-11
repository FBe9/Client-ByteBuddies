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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class ExerciseController {

    @FXML
    private TableView<?> tbvUnit;
    @FXML
    private TableColumn<?, ?> tbcName;
    @FXML
    private TableColumn<?, ?> tbcSubject;
    @FXML
    private TableColumn<?, ?> tbcDescription;
    @FXML
    private TableColumn<?, ?> tbcDateInit;
    @FXML
    private TableColumn<?, ?> tbcDateEnd;
    @FXML
    private TableColumn<?, ?> tbcHours;
    @FXML
    private TableColumn<?, ?> tbcExercises;
    @FXML
    private TextField tfSearch;
    @FXML
    private ComboBox<?> cbSubjects;
    @FXML
    private ComboBox<?> cbSearchType;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnDeleteUnit;
    @FXML
    private Button btnCreateUnit;
    @FXML
    private Button btnReturn;
    @FXML
    private Button btnPrint;

    /**
     * Initializes the controller class.
     */   
    
}
