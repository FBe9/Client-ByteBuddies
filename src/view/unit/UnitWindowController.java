package view.unit;

import exceptions.FindErrorException;
import factories.*;
import interfaces.SubjectManager;
import interfaces.UnitInterface;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jxl.Hyperlink;
import models.Subject;
import models.Teacher;
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
    private TableColumn<Unit, String> tbcName;
    @FXML
    private TableColumn<Unit, Subject> tbcSubject;
    @FXML
    private TableColumn<Unit, String> tbcDescription;
    @FXML
    private TableColumn<Unit, Date> tbcDateInit;
    @FXML
    private TableColumn<Unit, Date> tbcDateEnd;
    @FXML
    private TableColumn<Unit, String> tbcHours;
    @FXML
    private TableColumn<Unit, Hyperlink> tbcExercises;
    @FXML
    private TextField tfSearch;
    @FXML
    private ComboBox cbSubjects;
    @FXML
    private ComboBox cbSearchType;
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
    private User loggedUser;
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
            //Conseguir el modelo de la interfaz mediante la factoria.
            clientU = UnitFactory.getModel();
            clientS = SubjectFactory.getModel();
            //Crear la escena.
            Scene scene = new Scene(root);
            //Establecer la escena.
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
            if (loggedUser instanceof Teacher) {
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
            dpSearch.setValue(null);
            //Se rellena el combobox “cbSubjects” con una lista de asignaturas en las que esté registrado el usuario conectado a la aplicación. El usuario puede ser de dos tipos:
            if (loggedUser.getUser_type().equalsIgnoreCase("Teacher")) {
                try {
                    //Si el usuario es de tipo “Teacher”: Usar el método “findSubjectsByTeacherId” para rellenar el combobox pasandole el id del usuario conectado a la aplicación. 
                    clientsDataS = FXCollections.observableArrayList(clientS.findSubjectsByTeacherId(loggedUser.getId().toString()));
                    if (clientsDataS.isEmpty()) {
                        //Si el método no devuelve nada se rellena con el texto ”No Subjects found” y lo selecciona.
                        cbSubjects.getItems().add("No Subjects found");
                        cbSubjects.getSelectionModel().selectFirst();
                    } else {
                        List<String> subjectsNames = new ArrayList<>();
                        for (int i = 0; i < clientsDataS.size(); i++) {
                            String subjectName = clientsDataS.get(i).getName();
                            subjectsNames.add(subjectName);
                        }
                        cbSubjects.getItems().addAll(subjectsNames);
                    }
                } catch (FindErrorException e) {
                    LOGGER.log(Level.SEVERE, "Error searching for teacher subjects");
                }
            } else {
                try {
                    //Si el usuario es de tipo “Student”: Usar el método “findByEnrollments” para rellenar el combobox pasandole el id del usuario conectado a la aplicación. 
                    clientsDataS = FXCollections.observableArrayList(clientS.findByEnrollments(loggedUser.toString()));
                    if (clientsDataS.isEmpty()) {
                        //Si el método no devuelve nada se rellena con el texto ”No Subjects found” y lo selecciona.
                        cbSubjects.getItems().add("No Subjects found");
                        cbSubjects.getSelectionModel().selectFirst();
                    } else {
                        List<String> subjectsNames = new ArrayList<>();
                        for (int i = 0; i < clientsDataS.size(); i++) {
                            String subjectName = clientsDataS.get(i).getName();
                            subjectsNames.add(subjectName);
                        }
                        cbSubjects.getItems().addAll(subjectsNames);
                    }
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Error searching for student subjects");
                }
            }
            //Se rellena el combobox “cbSearchType” con los tipos de búsqueda: Name, Date Init, Date End and Hours.
            cbSearchType.getItems().addAll("Name", "Date Init", "Date End", "Hours");
            //El combobox “cbSubjects” no tendrá ninguna opción por defecto y el combobox “cbSearchType” tendrá la opción de “Name” por defecto.
            cbSearchType.getSelectionModel().selectFirst();
            //Los botones “btnSearch” y “btnDeleteUnit” estarán deshabilitados.
            btnSearch.setDisable(true);
            btnDeleteUnit.setDisable(true);
            //La tabla mostrará los atributos: Name(tbcName), Subject(tbcSubject), Description(tbcDescription), Date Init(tbcDateInit), Date End(tbcDateEnd), Hours(tbcHours) and Exercises(tbcExercises).
            tbcName.setCellValueFactory(
                    new PropertyValueFactory<>("name"));
            tbcName.setCellFactory(TextFieldTableCell.<Unit>forTableColumn());
            tbcSubject.setCellValueFactory(
                    new PropertyValueFactory<>("subject"));
            tbcSubject.setCellFactory(ComboBoxTableCell.forTableColumn(cbSubjects.getItems()));
            tbcDescription.setCellValueFactory(
                    new PropertyValueFactory<>("description"));
            tbcDescription.setCellFactory(TextFieldTableCell.<Unit>forTableColumn());
            tbcDateInit.setCellValueFactory(
                    new PropertyValueFactory<>("dateInit"));
            //tbcDateInit.setCellFactory(DateUnitEditingCell);
            tbcDateEnd.setCellValueFactory(
                    new PropertyValueFactory<>("dateEnd"));
            //CellFacttoy Date
            tbcHours.setCellValueFactory(
                    new PropertyValueFactory<>("hours"));
            tbcHours.setCellFactory(TextFieldTableCell.<Unit>forTableColumn());
            tbcExercises.setCellValueFactory(
                    new PropertyValueFactory<>("exercises"));
            //CellFactory Hyperlink

            //Charge tables data
            clientsDataU = FXCollections.observableArrayList(clientU.findUnitsFromTeacherSubjects(loggedUser.getId().toString()));
            tbvUnit.setItems((ObservableList) clientsDataU);
            tbvUnit.refresh();

            cbSearchType.getSelectionModel().selectedItemProperty().addListener(this::handleOnSelectSearchType);
            stage.setOnCloseRequest(this::handleOnActionExit);
            tfSearch.textProperty().addListener(this::textPropertyChange);
            //dpSearch.ValueProperty().addListener(this::textPropertyChange);
            stage.show();
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }

    }

    /**
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void handleOnSelectSearchType(ObservableValue<Object> observable,
            Object oldValue, Object newValue) {
        //Comprobar el valor seleccionado en el combobox: 
        if (cbSearchType.getSelectionModel().isSelected(0) || cbSearchType.getSelectionModel().isSelected(3)) {
            //Si el valor es Name o Hours: El campo textfield se volverá visible y el datepicker se volverá invisible.
            tfSearch.setVisible(true);
            dpSearch.setVisible(false);
        } else {
            //Si el valor es Date Init o Date End: El campo textfield se volverá invisible y el datepicker se volverá visible.
            tfSearch.setVisible(false);
            dpSearch.setVisible(true);
        }
    }

    /**
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    public void textPropertyChange(ObservableValue observable, String oldValue, String newValue) {
        //Validar cuál de los campos es visible, si el text field “tfSearch” o el datepicker “dpSearch”.
        if (tfSearch.isVisible()) {
            //Validar que el campo visible está informado. 
            if (tfSearch.getText().equalsIgnoreCase("")) {
                //En el caso de que no lo esté, deshabilitar el botón Search.
                btnSearch.setDisable(true);
            } else {
                //En el caso de que esté informado, habilitar el botón Search.
                btnSearch.setDisable(false);
            }
        } else {
            //Validar que el campo visible está informado.
            if (dpSearch.getValue() == null) {
                //En el caso de que no lo esté, deshabilitar el botón Search.
                btnSearch.setDisable(true);
            } else {
                //En el caso de que esté informado, habilitar el botón Search.
                btnSearch.setDisable(false);
            }
        }
        //El límite de caracteres de los campos será de 300 caracteres. Si el usuario excede este límite no le permitirá escribir más.
        if (tfSearch.getText().trim().length() > 300) {
            tfSearch.setText(tfSearch.getText().substring(0, 300));
            new Alert(Alert.AlertType.ERROR, "The maximum lenght for the login is 25 characters.", ButtonType.OK).showAndWait();
        }
    }

    /**
     *
     * @param event
     */
    public void handelSearchButtonAction(Event event) {
        //Comprobar el valor del combobox “cbSubjects”:
        //Ningún valor seleccionado: Avisar al usuario mediante una alerta de que seleccione una asignatura para hacer las búsquedas.
        //Si el valor es cualquier otro: Comprobar el valor del combobox “cbSearchType”:
        //Si el valor es Name: Se llamará a la factoría “UnitFactory” para llamar a la implementación de la interfaz “UnitInterface” y se usará el método “findSubjectUnitsByName” para rellenar la tabla pasandole el nombre de la subject seleccionada en la combobox y el nombre de la unidad escrita en el textfield. 
        //Si el valor es Date Init: Se llamará a la factoría “UnitFactory” para llamar a la implementación de la interfaz “UnitInterface” y se usará el método “findSubjectUnitsByDateInit” para rellenar la tabla pasandole el nombre de la subject seleccionada en la combobox y la fecha de la unidad escrita en el datePicker. 
        //Si el valor es Date End: Se llamará a la factoría “UnitFactory” para llamar a la implementación de la interfaz “UnitInterface” y se usará el método “findSubjectUnitsByDateEnd” para rellenar la tabla pasandole el nombre de la subject seleccionada en la combobox y la fecha de la unidad escrita en el datePicker. 
        //Si el valor es Hours: Se llamará a la factoría “UnitFactory” para llamar a la implementación de la interfaz “UnitInterface” y se usará el método “findSubjectUnitsByHours” para rellenar la tabla pasandole el nombre de la subject seleccionada en la combobox y el numero de horas escrita en el textfield. 
    }

    /**
     *
     * @param event
     */
    public void handelDeleteButtonAction(Event event) {
        //Pedir confirmación al usuario para eliminar la unit seleccionada:
        //Si no confirma: mantenerse en la ventana.
        //Si el usuario confirma: Se llamará a la factoría “UnitFactory” para llamar a la implementación de la interfaz “UnitInterface” y se usará el método “removeUnit” para eliminar la unit pasandole la unit seleccionada en un objeto de tipo Unit. 
        //Si no se ha producido ningún error, la tabla se actualizará.
        //Si se produce algún error, se le mostrará al usuario una alerta con el error  y se cancelará la eliminación de la unit.
    }

    /**
     *
     * @param event
     */
    public void handelCreateButtonAction(Event event) {
        //Se creará una nueva fila en la tabla con valores por defecto:
        //Las columnas Name y Description estarán vacías.
        //La columna Subject en caso de que una subject esté seleccionada en el combobox “cbSubjects”, se selecciona esta en este campo.
        //Si no es el caso, será la posición 1 del combobox “cbSubjects” la que se seleccione.
        //Las columnas de Date Init y Date  End estarán cargadas con la fecha de hoy (Init) y la de mañana (End).
        //La columna de Hours estará a 0.
        //La columna de Exercises tendrá el valor “View Exercises”.
        //Después se llamará a la factoría “UnitFactory” para llamar a la implementación de la interfaz “UnitInterface” y se usará el método “createUnit” para crear una Unit con los valores por defecto que hemos estipulado pasandoselos en un objeto de tipo Unit.
        //Si la operación se lleva a cabo sin errores, la fila recién creada se mostrará en la tabla.
        //Si se produce algún error, se le mostrará al usuario una alerta con el error y se cancelará la creación de la asignatura.
    }

    /**
     *
     * @param event
     */
    public void handelPrinteButtonAction(Event event) {
        /*Se inicia la acción de impresión de los datos que tenga la tabla de Units en ese momento con todas las columnas de la misma.
         */
    }

    /**
     * Exit button event handler.
     *
     * @param event An ActionEvent object.
     */
    public void handleOnActionExit(Event event) {
        try {
            //Ask user for confirmation on exit
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "¿Are you sure you want to exit?",
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

    /**
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
