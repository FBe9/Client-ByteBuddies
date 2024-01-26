package view.unit;

import exceptions.CreateErrorException;
import exceptions.DeleteErrorException;
import exceptions.FindErrorException;
import factories.*;
import interfaces.SubjectManager;
import interfaces.UnitInterface;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Subject;
import models.Teacher;
import models.Unit;
import models.User;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import view.MenuBarController;

/**
 * FXML Controller class
 *
 * @author Nerea
 */
public class UnitWindowController {

    @FXML
    private TableView<Unit> tbvUnit;
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
    private TableColumn<Unit, String> tbcExercises;
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
    private Subject subject;
    private User loggedUser;
    private UnitInterface clientU;
    private SubjectManager clientS;
    @FXML
    private MenuItem cmiCreateUnit;
    @FXML
    private MenuItem cmiDeleteUnit;
    @FXML
    private MenuItem cmiPrintReport;

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
            this.loggedUser = loggedUser;
            //Comprobar que tipo de usuario está conectado a la aplicación:
            if (loggedUser instanceof Teacher) {
                //En el caso del “Teacher”: Tendrá las opciones CRUD disponibles, así como el menú de contexto que aparecerá al hacer clic derecho en la tabla. 
                btnCreateUnit.setVisible(true);
                btnDeleteUnit.setVisible(true);
                tbvUnit.setEditable(true);
                //La columna “Exercises” será la única que no se podrá editar.
                tbcExercises.setEditable(false);

                /**
                 * Al hacer clic derecho en una fila de la tabla se mostrará un
                 * menú de contexto con las siguientes opciones: “Create new
                 * Unit”, “Delete Unit” y “Create a report”: Si se selecciona
                 * “Create new Unit”: Se llamará al método del controlador de la
                 * ventana de Units, “handelCreateButtonAction”, que será el
                 * método que controle el evento de pulsación del botón
                 * “btnCreateUnit”.
                 */
                cmiCreateUnit.setOnAction(this::handelCreateButtonAction);
                /**
                 * Si se selecciona “Delete Unit”: Se llamará al método del
                 * controlador de la ventana de Units,
                 * “handelDeleteButtonAction”, que será el método que controle
                 * el evento de pulsación del botón “btnDeleteUnit”.
                 */
                cmiDeleteUnit.setOnAction(this::handelDeleteButtonAction);
                /**
                 * Si se selecciona “Create a report”: Se llamará al método del
                 * controlador de la ventana de Units,
                 * “handelPrinteButtonAction”, que será el método que controle
                 * el evento de pulsación del botón “btnPrint”.
                 */
                cmiPrintReport.setOnAction(this::handelPrinteButtonAction);

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
                } catch (FindErrorException e) {
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
            tbcName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tbcSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
            tbcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            tbcDateInit.setCellValueFactory(new PropertyValueFactory<>("dateInit"));
            tbcDateEnd.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
            tbcHours.setCellValueFactory(new PropertyValueFactory<>("hours"));
            tbcHours.setCellFactory(TextFieldTableCell.<Unit>forTableColumn());
            //La tabla “tbvUnit” será en su mayoría editable. En modo edición:
            //Las columnas “Name”, “Description” y “Hours” serán textfields.
            tbcName.setCellFactory(TextFieldTableCell.<Unit>forTableColumn());
            tbcDescription.setCellFactory(TextFieldTableCell.<Unit>forTableColumn());
            tbcExercises.setCellValueFactory(new PropertyValueFactory<>("exercises"));
            //La columna “Subjects” será un combobox, cargado con los mismos valores que la combobox “cbSubjects”. 
            tbcSubject.setCellFactory(ComboBoxTableCell.forTableColumn(cbSubjects.getItems()));
            //Las columnas “Date Init” y “Date End” serán datepickers.
            final Callback<TableColumn<Unit, Date>, TableCell<Unit, Date>> dateCell
                    = (TableColumn<Unit, Date> param) -> new DateUnitEditingCell();
            tbcDateInit.setCellFactory(dateCell);
            tbcDateEnd.setCellFactory(dateCell);
            //Exercises no corresponde a ninguna base de datos dado que mostrará un hiperlink con el nombre “View Exercises” para cambiar a la ventana de Exercises.
            final Callback<TableColumn<Unit, String>, TableCell<Unit, String>> hyperlinkExercisesCell
                    = (TableColumn<Unit, String> param) -> new HyperlinkUnitEditingCell(this.loggedUser, stage);
            tbcExercises.setCellFactory(hyperlinkExercisesCell);
            //Las columnas de fechas las mostrará con un patrón formateado de acuerdo a la configuración del sistema.

            //Se carga la tabla en base de la conbobox de Subjects.
            actualizarTabla();

            cbSubjects.getSelectionModel().selectedItemProperty().addListener(this::handleOnSelectSubjects);
            cbSearchType.getSelectionModel().selectedItemProperty().addListener(this::handleOnSelectSearchType);
            stage.setOnCloseRequest(this::handleOnActionExit);
            tfSearch.textProperty().addListener(this::textPropertyChange);
            //dpSearch.ValueProperty().addListener(this::textPropertyChange);
            tbvUnit.getSelectionModel().selectedItemProperty().addListener(this::handleUnitTableSelectionChanged);
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
    public void handleOnSelectSubjects(ObservableValue<Object> observable,
            Object oldValue, Object newValue) {
        try {
            //Comprobar qué valor está seleccionado:
            String selectedValue = (String) cbSubjects.getSelectionModel().getSelectedItem();
            if (selectedValue.isEmpty()) {
                //Si no hay nada seleccionado: Se carga la tabla con valor de todas las unidades de todas las asignaturas en las que esté registrado el usuario conectado a la aplicación. El usuario puede ser de dos tipos:
                if (loggedUser.getUser_type().equalsIgnoreCase("Teacher")) {
                    //Si el usuario es de tipo “Teacher”: Se usará el método “findUnitsFromTeacherSubjects” para rellenar la tabla pasandole el id del usuario conectado a la aplicación. 
                    clientsDataU = FXCollections.observableArrayList(clientU.findUnitsFromTeacherSubjects(loggedUser.getId().toString()));
                    tbvUnit.setItems((ObservableList) clientsDataU);
                    tbvUnit.refresh();
                } else {
                    //Si el usuario es de tipo “Student”: Se usará el método “findUnitsFromStudentSubjects” para rellenar la tabla pasandole el id del usuario conectado a la aplicación. 
                    clientsDataU = FXCollections.observableArrayList(clientU.findUnitsFromStudentSubjects(loggedUser.getId().toString()));
                    tbvUnit.setItems((ObservableList) clientsDataU);
                    tbvUnit.refresh();
                }
            } else if (selectedValue.equalsIgnoreCase("No Subjects found")) {
                //Si está seleccionado ”No Subjects found”: el combobox de search type(cbSearchType), el textfield(tfSearch), el datepicker(dpSearch) y los botones de search(btnSearch), create(btnCreateUnit) y delete(btnDeleteUnit) se desactivan.
                cbSearchType.setDisable(true);
                tfSearch.setDisable(true);
                dpSearch.setDisable(true);
                btnSearch.setDisable(true);
                btnCreateUnit.setDisable(true);
                btnDeleteUnit.setDisable(true);
            } else {
                //Si hay un valor: Se usará el método “findSubjectUnits” para rellenar la tabla pasandole el nombre de la subject seleccionada en la combobox. 
                clientsDataU = FXCollections.observableArrayList(clientU.findSubjectUnits(selectedValue));
                tbvUnit.setItems((ObservableList) clientsDataU);
                tbvUnit.refresh();
            }
        } catch (FindErrorException e) {
            LOGGER.severe(e.getMessage());
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
        tfSearch.setText("");
        dpSearch.setValue(null);
        actualizarTabla();
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
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void handleUnitTableSelectionChanged(ObservableValue observable, Object oldValue, Object newValue) {
        Unit unit = (Unit) newValue;
        if (newValue != null) {
            btnDeleteUnit.setDisable(false);
        } else {
            btnDeleteUnit.setDisable(true);
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    public void handelSearchButtonAction(Event event) {
        LOGGER.info("Starting Search button action method");
        String subjectValue;
        String searchValue = null;
        try {
            //Comprobar el valor del combobox “cbSubjects”:
            subjectValue = (String) cbSubjects.getSelectionModel().getSelectedItem();
            if (cbSubjects.getSelectionModel().isEmpty()) {
                //Ningún valor seleccionado: Avisar al usuario mediante una alerta de que seleccione una asignatura para hacer las búsquedas.
                new Alert(Alert.AlertType.ERROR, "Please select a subject in the combobox to continue searching", ButtonType.OK).showAndWait();
            } else {
                //Si el valor es cualquier otro: Comprobar el valor del combobox “cbSearchType”:
                searchValue = (String) cbSearchType.getSelectionModel().getSelectedItem();
                if (searchValue.equalsIgnoreCase("Name")) {
                    //Si el valor es Name: Se rellena la tabla con el método “findSubjectUnitsByName” pasandole el nombre de la subject seleccionada en la combobox y el nombre de la unidad escrita en el textfield. 
                    clientsDataU = FXCollections.observableArrayList(clientU.findSubjectUnitsByName(tfSearch.getText(), subjectValue));
                    tbvUnit.setItems(clientsDataU);
                    tbvUnit.refresh();

                } else if (searchValue.equalsIgnoreCase("Date Init")) {
                    //Si el valor es Date Init: Se rellena la tabla con el método “findSubjectUnitsByDateInit” pasandole el nombre de la subject seleccionada en la combobox y la fecha de la unidad escrita en el datePicker. 
                    LocalDate datePicker = dpSearch.getValue();
                    Date date = Date.from(datePicker.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    clientsDataU = FXCollections.observableArrayList(clientU.findSubjectUnitsByDateInit(date, subjectValue));
                    tbvUnit.setItems((ObservableList) clientsDataU);
                    tbvUnit.refresh();
                } else if (searchValue.equalsIgnoreCase("Date End")) {
                    //Si el valor es Date End: Se usará el método “findSubjectUnitsByDateEnd” para rellenar la tabla pasandole el nombre de la subject seleccionada en la combobox y la fecha de la unidad escrita en el datePicker. 
                    LocalDate datePicker = dpSearch.getValue();
                    Date date = Date.from(datePicker.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    clientsDataU = FXCollections.observableArrayList(clientU.findSubjectUnitsByDateEnd(date, subjectValue));
                    tbvUnit.setItems((ObservableList) clientsDataU);
                    tbvUnit.refresh();
                } else if (searchValue.equalsIgnoreCase("Hours")) {
                    //Si el valor es Hours: Se usará el método “findSubjectUnitsByHours” para rellenar la tabla pasandole el nombre de la subject seleccionada en la combobox y el numero de horas escrita en el textfield. 
                    clientsDataU = FXCollections.observableArrayList(clientU.findSubjectUnitsByHours(tfSearch.getText(), subjectValue));
                    tbvUnit.setItems((ObservableList) clientsDataU);
                    tbvUnit.refresh();
                }

            }
            if (clientsDataU.isEmpty()) {
                new Alert(Alert.AlertType.INFORMATION, "There is no Unit with that " + searchValue, ButtonType.OK).showAndWait();
            }
        } catch (FindErrorException e) {
            new Alert(Alert.AlertType.INFORMATION, "There is no Unit with that " + searchValue, ButtonType.OK).showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION, "Error:" + e.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    public void handelDeleteButtonAction(Event event) {
        try {
            Unit unit = (Unit) tbvUnit.getSelectionModel().getSelectedItem();
            if (unit == null) {
                new Alert(Alert.AlertType.INFORMATION, "Please, select a unit to delete", ButtonType.OK).showAndWait();
            } else {
                //Pedir confirmación al usuario para eliminar la unit seleccionada:
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "¿Are you sure you want to delete this unit?",
                        ButtonType.OK, ButtonType.CANCEL);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    //Si el usuario confirma: Se usará el método “removeUnit” para eliminar la unit pasandole la unit seleccionada en un objeto de tipo Unit. 
                    clientU.removeUnit(unit);
                } else {
                    //Si no confirma: mantenerse en la ventana.
                    event.consume();
                }
                //Si no se ha producido ningún error, la tabla se actualizará.
                actualizarTabla();
            }
            //Si se produce algún error, se le mostrará al usuario una alerta con el error  y se cancelará la eliminación de la unit.
        } catch (DeleteErrorException e) {
            new Alert(Alert.AlertType.INFORMATION, "Error while deleting the Unit", ButtonType.OK).showAndWait();
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    public void handelCreateButtonAction(Event event) {
        try {
            //Se creará una nueva fila en la tabla con valores por defecto:
            Unit newUnit = new Unit();
            //Las columnas Name y Description estarán vacías.
            newUnit.setName("");
            newUnit.setDescription("");

            String subjectUnit = (String) cbSubjects.getSelectionModel().getSelectedItem();
            if (!cbSubjects.getSelectionModel().isEmpty()) {
                //La columna Subject en caso de que una subject esté seleccionada en el combobox “cbSubjects”, se selecciona esta en este campo.
                clientsDataS = FXCollections.observableArrayList(clientS.findAllSubjects());
                for (int i = 0; i < clientsDataS.size(); i++) {
                    if (clientsDataS.get(i).getName().equalsIgnoreCase(subjectUnit)) {
                        newUnit.setSubject(clientsDataS.get(i));
                        i = clientsDataS.size();
                    }
                }
            } else {
                //Si no es el caso, será la posición 1 del combobox “cbSubjects” la que se seleccione.
                List<String> list = cbSubjects.getItems();
                list.get(1);
                clientsDataS = FXCollections.observableArrayList(clientS.findAllSubjects());
                for (int i = 0; i < clientsDataS.size(); i++) {
                    if (clientsDataS.get(i).getName().equalsIgnoreCase((String) list.get(0))) {
                        newUnit.setSubject(clientsDataS.get(i));
                        i = clientsDataS.size();
                    }
                }
            }
            //Las columnas de Date Init y Date  End estarán cargadas con la fecha de hoy (Init) y la de mañana (End).
            newUnit.setDateInit(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            newUnit.setDateEnd(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

            //La columna de Hours estará a 0.
            newUnit.setHours("0");
            //La columna de Exercises tendrá el valor “View Exercises”.
            Boolean create = true;
            List<Unit> units = clientU.findSubjectUnits(subjectUnit);
            for (int i = 0; i < units.size(); i++) {
                if (units.get(i).getName().equalsIgnoreCase("")) {
                    create = false;
                    i = units.size();
                }
            }
            if (create) {
                //Se usará el método “createUnit” para crear una Unit con los valores por defecto que hemos estipulado pasandoselos en un objeto de tipo Unit.
                clientU.createUnit(newUnit);
                //Si la operación se lleva a cabo sin errores, la fila recién creada se mostrará en la tabla.
                new Alert(Alert.AlertType.INFORMATION, "Unit added successfully", ButtonType.OK).showAndWait();
                actualizarTabla();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "There is a unit at this subject without name, please change it before creating a new one", ButtonType.OK).showAndWait();
            }

            //Si se produce algún error, se le mostrará al usuario una alerta con el error y se cancelará la creación de la asignatura.
        } catch (FindErrorException | CreateErrorException ex) {
            Logger.getLogger(UnitWindowController.class
                    .getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.INFORMATION, "There was a problem while creating the unit", ButtonType.OK).showAndWait();

        } catch (Exception ex) {
            Logger.getLogger(UnitWindowController.class
                    .getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.INFORMATION, ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    public void handelPrinteButtonAction(Event event) {
        /*Se inicia la acción de impresión de los datos que tenga la tabla de Units en ese momento con todas las columnas de la misma.
         */
        try {
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/UnitReport.jrxml"));
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((List<Unit>) this.tbvUnit.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
        }
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

    private void superRefresh() {
        try {
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("UnitWindow.fxml"));
            Parent root = (Parent) loader.load();
            // Obtain the Sign In window controller
            UnitWindowController controller = (UnitWindowController) loader.getController();
            controller.setStage(stage);
            controller.initStage(root, loggedUser);

        } catch (IOException ex) {
            Logger.getLogger(MenuBarController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void actualizarTabla() {
        try {
            if (cbSubjects.getSelectionModel().isEmpty()) {
                //Si no hay nada seleccionado: Se carga la tabla con valor de todas las unidades de todas las asignaturas en las que esté registrado el usuario conectado a la aplicación. El usuario puede ser de dos tipos:
                if (loggedUser.getUser_type().equalsIgnoreCase("Teacher")) {

                    //Si el usuario es de tipo “Teacher”: Se usará el método “findUnitsFromTeacherSubjects” para rellenar la tabla pasandole el id del usuario conectado a la aplicación.
                    clientsDataU = FXCollections.observableArrayList(clientU.findUnitsFromTeacherSubjects(loggedUser.getId().toString()));

                    tbvUnit.setItems((ObservableList) clientsDataU);
                    tbvUnit.refresh();
                } else {
                    //Si el usuario es de tipo “Student”: Se usará el método “findUnitsFromStudentSubjects” para rellenar la tabla pasandole el id del usuario conectado a la aplicación. 
                    clientsDataU = FXCollections.observableArrayList(clientU.findUnitsFromStudentSubjects(loggedUser.getId().toString()));
                    tbvUnit.setItems((ObservableList) clientsDataU);
                    tbvUnit.refresh();
                }
            } else {
                //Si hay un valor: Se usará el método “findSubjectUnits” para rellenar la tabla pasandole el nombre de la subject seleccionada en la combobox. 
                clientsDataU = FXCollections.observableArrayList(clientU.findSubjectUnits((String) cbSubjects.getSelectionModel().getSelectedItem()));
                tbvUnit.setItems((ObservableList) clientsDataU);
                tbvUnit.refresh();

            }
        } catch (FindErrorException ex) {
            Logger.getLogger(UnitWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setCurrentSubject(Subject subject) {
        this.subject = subject;
        String subjectName = subject.toString();
        cbSubjects.getSelectionModel().select(subjectName);
    }
}
