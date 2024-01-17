/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.unit;

import exceptions.FindErrorException;
import factories.UnitFactory;
import interfaces.UnitInterface;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Unit;
import models.User;

/**
 * FXML Controller class
 *
 * @author 2dam
 */
public class UnitWindowController {

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
    private Button btnPrint;
//The stage of the window.
    private Stage stage;
    //Logger for the aplication. 
    private static final Logger LOGGER = Logger.getLogger("package view.Unit");
    private ObservableList<Unit> clientsData;
    private User loggedUser = new User();
    private UnitInterface client;
    
    /**
     * Initializes the controller class.
     * @param root
     * @param loggedUser
     */
    public void initStage(Parent root, User loggedUser) {
        LOGGER.info("Initializing Unit window");
        this.loggedUser = loggedUser;

        //Creates an scene
        Scene scene = new Scene(root);
        //Establishes an scene
        stage.setScene(scene);
        //Window title
        stage.setTitle("Albums");
        //Not resizable window
        stage.setResizable(false);
        
        //Set factories for cell values in users table columns (My albums table)
        tbcName.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        
        tbcDescription.setCellValueFactory(
                new PropertyValueFactory<>("description"));
        tbcDateInit.setCellValueFactory(
                new PropertyValueFactory<>("dateInit"));
        tbcDateInit.setCellValueFactory(
                new PropertyValueFactory<>("dateEnd"));
        
         //Charge tables data
    try {
        client = UnitFactory.getModel();
        clientsData = FXCollections.observableArrayList(client.findAllUnits());

        tbvUnit.setItems((ObservableList<?>) clientsData);
        tbvUnit.refresh();

    } catch (FindErrorException e) {
        LOGGER.info(e.getMessage());
}
    }
}
