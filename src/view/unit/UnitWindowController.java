/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.unit;

import factories.*;
import interfaces.SubjectManager;
import interfaces.UnitInterface;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.Subject;
import models.Unit;
import models.User;

/**
 * FXML Controller class
 *
 * @author Nerea
 */
public class UnitWindowController {
    
    @FXML
    private TableView tbvUnit;
    @FXML
    private TableColumn tbcName;
    @FXML
    private TableColumn tbcSubject;
    @FXML
    private TableColumn tbcDescription;
    @FXML
    private TableColumn tbcDateInit;
    @FXML
    private TableColumn tbcDateEnd;
    @FXML
    private TableColumn tbcHours;
    @FXML
    private TableColumn tbcExercises;
    @FXML
    private TextField tfSearch;
    @FXML
    private ComboBox<String> cbSubjects;
    @FXML
    private ComboBox<String> cbSearchType;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnDeleteUnit;
    @FXML
    private Button btnCreateUnit;
    @FXML
    private Button btnPrint;
    @FXML
    private DatePicker dpSearch;

    //The stage of the window.
    private Stage stage;
    //Logger for the aplication. 
    private static final Logger LOGGER = Logger.getLogger("package view.Unit");
    private ObservableList<Unit> clientsDataU;
    private ObservableList<Subject> clientsDataS;
    private List<String> Subjects;
    private User loggedUser = new User();
    private UnitInterface clientU;
    private SubjectManager clientS;

    /**
     * Initializes the controller class.
     *
     * @param root
     * @param loggedUser
     */
    public void initStage(Parent root, User loggedUser) {
        try {
            LOGGER.info("Initializing Unit window");
            //Creates an scene
            Scene scene = new Scene(root);
            //Establishes an scene
            stage.setScene(scene);
            //El nombre de la ventana es “Units”.
            stage.setTitle("Units");
            //Añadir a la ventana un icono de una estrella.
            stage.getIcons().add(new Image("resources/BlueStar.png"));
            //Ventana no redimensionable.
            stage.setResizable(false);
            //Ventana no modal.
            //Se añade el MenuBar.fxml a nuestra ventana.
            //La ventana de Sign In nos pasará un Objeto User con los datos del usuario registrado en la aplicación.
            this.loggedUser = loggedUser;
            //Comprobar que tipo de usuario está conectado a la aplicación:
            if (loggedUser.getUser_type().equalsIgnoreCase("Teacher")) {
                //En el caso del “Teacher”: Tendrá las opciones CRUD disponibles, así como el menú de contexto que aparecerá al hacer clic derecho en la tabla. 
                btnCreateUnit.setVisible(true);
                btnDeleteUnit.setVisible(true);
                tbvUnit.setEditable(true);
            } else {
                //En el caso del “Student”: Tendrá solo las consultas disponibles y el botón de create y delete se ocultaran y no le permitirá desplegar ni usar el menú de contexto.
                btnCreateUnit.setVisible(false);
                btnDeleteUnit.setVisible(false);
                tbvUnit.setEditable(false);
            }
            //El campo “tfSearch” será visible y el date picker “dpSearch” estará invisible.
            tfSearch.setVisible(true);
            dpSearch.setVisible(false);
            //Se vacían el text field  “tfSearch” y el datepicker “dpSearch”.
            tfSearch.setText("");
            //Se rellena el combobox “cbSubjects” con una lista de asignaturas en las que esté registrado el usuario conectado a la aplicación. El usuario puede ser de dos tipos:
            if (loggedUser.getUser_type().equalsIgnoreCase("Teacher")) {
                try{
                //Si el usuario es de tipo “Teacher” se llamará a la factoría “SubjectFactory” para llamar a la implementación de la interfaz “SubjectManager” y se usará el método “findSubjectsByTeacherId” para rellenar el combobox pasandole el id del usuario conectado a la aplicación. 
                clientS = SubjectFactory.getModel();
                clientsDataS = (ObservableList)FXCollections.observableList((List<Subject>) clientS.findSubjectsByTeacherId(loggedUser.getId()));
                if(clientsDataS.isEmpty()){
                    //Si el método no devuelve nada se rellena con el texto ”No Subjects found” y lo selecciona.
                cbSubjects.getItems().add("No Subjects found");
                cbSubjects.getSelectionModel().selectFirst();
                }
                cbSubjects.setItems((ObservableList)clientsDataS);
                 
                }catch(Exception e){
                
                }
            } else {
                //Si el usuario es de tipo “Student” se llamará a la factoría “SubjectFactory” para llamar a la implementación de la interfaz “SubjectManager” y usará el método “findByEnrollments” para rellenar el combobox pasandole el id del usuario conectado a la aplicación. 
                //Si el método no devuelve nada se rellena con el texto ”No Subjects found” y lo selecciona. 

            }


            //Set factories for cell values in units table columns 
            tbcName.setCellValueFactory(
                    new PropertyValueFactory<>("name"));
            tbcName.setCellFactory(TextFieldTableCell.<Unit>forTableColumn());
            //Subjectt?
            tbcDescription.setCellValueFactory(
                    new PropertyValueFactory<>("description"));
            tbcDescription.setCellFactory(TextFieldTableCell.<Unit>forTableColumn());
            tbcDateInit.setCellValueFactory(
                    new PropertyValueFactory<>("dateInit"));
            tbcDateInit.setCellValueFactory(
                    new PropertyValueFactory<>("dateEnd"));
            tbcHours.setCellValueFactory(
                    new PropertyValueFactory<>("hours"));
            tbcHours.setCellFactory(TextFieldTableCell.<Unit>forTableColumn());
            //Exercises

            //Charge tables data
            clientU = UnitFactory.getModel();
            clientsDataU = FXCollections.observableList(clientU.findAllUnits());
            tbvUnit.setItems((ObservableList) clientsDataU);
            tbvUnit.refresh();
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
    }
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
