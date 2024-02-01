package view.unit;

import exceptions.*;
import factories.*;
import interfaces.SubjectManager;
import interfaces.UnitInterface;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import models.Subject;
import models.Teacher;
import models.Unit;
import models.User;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Controlador de la ventana de UnitWindow.fxml.
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
            //Añadir a la ventana el logo de la aplicación.
            stage.getIcons().add(new Image("resources/Logo.jpg"));
            //Ventana no redimensionable.
            stage.setResizable(false);
            //Ventana no modal.
            this.loggedUser = loggedUser;
            //Comprobar que tipo de usuario está conectado a la aplicación:
            if (loggedUser.getUser_type().equalsIgnoreCase("Teacher")) {
                //En el caso del “Teacher”: Tendrá las opciones CRUD disponibles, así como el menú de contexto que aparecerá al hacer clic derecho en la tabla. 
                btnCreateUnit.setVisible(true);
                btnDeleteUnit.setVisible(true);
                tbvUnit.setEditable(true);
                //La columna “Exercises” será la única que no se podrá editar.
                tbcExercises.setEditable(false);
                /**
                 * Al hacer clic derecho en una fila de la tabla se mostrará un
                 * menú de contexto con las siguientes opciones: “Create new
                 * Unit”, “Delete Unit” y “Create a report”:
                 */
                /**
                 * Si se selecciona “Create new Unit”: Se llamará al método del
                 * controlador de la ventana de Units,
                 * “handelCreateButtonAction”, que será el método que controle
                 * el evento de pulsación del botón “btnCreateUnit”.
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
                cmiPrintReport.setOnAction(this::handelPrintButtonAction);

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
            //Se le añadira un convertidor al date picker para mostrar el mismo formato de fecha que tiene la tabla.
            dpSearch.setConverter(new StringConverter<LocalDate>() {
                Locale locale = Locale.getDefault();
                // Formato de fecha para mostrar
                private final DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);

                // Convierte de LocalDate a String
                @Override
                public String toString(LocalDate localDate) {
                    if (localDate != null) {
                        return dateFormatter.format(java.sql.Date.valueOf(localDate));
                    }
                    return null;
                }

                // Convierte de String a LocalDate
                @Override
                public LocalDate fromString(String string) {
                    if (string != null && !string.isEmpty()) {
                        try {
                            java.util.Date date = dateFormatter.parse(string);
                            return date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                        } catch (ParseException e) {
                            LOGGER.severe("Error parsing the datePicker content " + e.getMessage());
                        }
                    }
                    return null;
                }
            });
            //Creamos un combobox para la celda editable de subject de la tabla.
            ComboBox<Subject> comboEdit = new ComboBox();
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
                        //Se rellena el combo con subjects para la celda de subject de la tabla.
                        comboEdit.getItems().addAll(clientsDataS);
                        cbSubjects.getItems().add(0, "All subjects units");
                        cbSubjects.getSelectionModel().selectFirst();
                    }
                } catch (FindErrorException e) {
                    LOGGER.log(Level.SEVERE, "Error searching for teacher subjects");
                }
            } else {
                try {
                    //Si el usuario es de tipo “Student”: Usar el método “findByEnrollments” para rellenar el combobox pasandole el id del usuario conectado a la aplicación. 
                    clientsDataS = FXCollections.observableArrayList(clientS.findByEnrollments(loggedUser.getId().toString()));
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
                        //Se rellena el combo con subjects para la celda de subject de la tabla.
                        comboEdit.getItems().addAll(clientsDataS);
                        cbSubjects.getItems().add(0, "All subjects units");
                        cbSubjects.getSelectionModel().selectFirst();
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
            //La columna “Subjects” será un combobox, cargado con los mismos valores que la combobox que hemos creado antes que tiene el valor de “cbSubjects” menos el valor "All subjects units". 
            tbcSubject.setCellFactory(ComboBoxTableCell.forTableColumn(comboEdit.getItems()));
            //Las columnas “Date Init” y “Date End” serán datepickers.
            //Las columnas de fechas las mostrará con un patrón formateado de acuerdo a la configuración del sistema (en su factoría de celdas)
            final Callback<TableColumn<Unit, Date>, TableCell<Unit, Date>> dateCell
                    = (TableColumn<Unit, Date> param) -> new DateUnitEditingCell();
            tbcDateInit.setCellFactory(dateCell);
            tbcDateEnd.setCellFactory(dateCell);
            //Exercises no corresponde a ninguna base de datos dado que mostrará un hiperlink con el nombre “View all Exercises” para cambiar a la ventana de Exercises.
            final Callback<TableColumn<Unit, String>, TableCell<Unit, String>> hyperlinkExercisesCell
                    = (TableColumn<Unit, String> param) -> new HyperlinkUnitEditingCell(this.loggedUser, stage);
            tbcExercises.setCellFactory(hyperlinkExercisesCell);
            //OnEditCommits
            tbcName.setOnEditCommit(this::onEditCommitColumnName);
            tbcSubject.setOnEditCommit(this::onEditCommitColumnSubject);
            tbcDescription.setOnEditCommit(this::onEditCommitColumnDescription);
            tbcDateInit.setOnEditCommit(this::onEditCommitColumnDateInit);
            tbcDateEnd.setOnEditCommit(this::onEditCommitColumnDateEnd);
            tbcHours.setOnEditCommit(this::onEditCommitColumnHours);
            //Se carga la tabla en base de la conbobox de Subjects.
            actualizarTabla();
            //Listeners de cambio de selección
            tbvUnit.getSelectionModel().selectedItemProperty().addListener(this::handleUnitTableSelectionChanged);
            cbSubjects.getSelectionModel().selectedItemProperty().addListener(this::handleOnSelectSubjects);
            cbSearchType.getSelectionModel().selectedItemProperty().addListener(this::handleOnSelectSearchType);
            //Close Request
            stage.setOnCloseRequest(this::handleOnActionExit);
            //TextProperty
            tfSearch.textProperty().addListener(this::textPropertyChange);
            dpSearch.getEditor().textProperty().addListener(this::textPropertyChange);
            //On Actions
            btnCreateUnit.setOnAction(this::handelCreateButtonAction);
            btnDeleteUnit.setOnAction(this::handelDeleteButtonAction);
            btnPrint.setOnAction(this::handelPrintButtonAction);
            //Mostrar la ventana.
            stage.show();
        } catch (Exception e) {
            //Si se produce cualquier excepción en esta respuesta mostrar un mensaje al usuario con el texto de la excepción.
            LOGGER.log(Level.SEVERE, "Error while initializeing the window: {0}", e.getMessage());
            new Alert(Alert.AlertType.ERROR, "Error while initializeing the window: {0}" + e.getMessage(), ButtonType.OK).showAndWait();
        }

    }

    /**
     * Método observable al cambio de selección del combobox cbSubjects. Las
     * tablas se deben de recargar dependiendo del valor de este.
     *
     * @param observable El combobox que se está observando.
     * @param oldValue El objeto con el valor anterior en el combo.
     * @param newValue El objeto con el valor actual en el combo.
     */
    public void handleOnSelectSubjects(ObservableValue<Object> observable,
            Object oldValue, Object newValue) {
        LOGGER.info("Starting cbSubjects selection change method");
        try {
            //Comprobar qué valor está seleccionado:
            String selectedValue = (String) cbSubjects.getSelectionModel().getSelectedItem();
            if (selectedValue.equalsIgnoreCase("All subjects units")) {
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
            LOGGER.log(Level.SEVERE, "Error while updating the table info: {0}", e.getMessage());
            new Alert(Alert.AlertType.ERROR, "Error while updating the table info: {0}" + e.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    /**
     * Método observable al cambio de selección del combobox cbSearchType. Los
     * valores de busqueda cambiaran dependiendo lo que se marque en este.
     *
     * @param observable El combobox que se está observando.
     * @param oldValue El objeto con el valor anterior en el combo.
     * @param newValue El objeto con el valor actual en el combo.
     */
    public void handleOnSelectSearchType(ObservableValue<Object> observable,
            Object oldValue, Object newValue) {
        LOGGER.info("Starting cbSearchType selection change method");
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
     * Método observable del cambio de texto del textfield tfSearch. El limite
     * de carácteres del mismo y cuando habilitar el botón de search.
     *
     * @param observable El textField siendo observado.
     * @param oldValue Un String con el valor anterior del textField.
     * @param newValue Un String con el valor actual del textField.
     */
    public void textPropertyChange(ObservableValue observable, String oldValue, String newValue) {
        //Validar cuál de los campos es visible, si el text field “tfSearch” o el datepicker “dpSearch”.
        if (tfSearch.isVisible()) {
            //Validar que el campo visible está informado. 
            if (tfSearch.getText().equalsIgnoreCase("")) {
                //En el caso de que no lo esté, deshabilitar el botón Search.
                btnSearch.setDisable(true);
                actualizarTabla();
            } else {
                //En el caso de que esté informado, habilitar el botón Search.
                btnSearch.setDisable(false);
            }
        } else {
            //Validar que el campo visible está informado.
            if (dpSearch.getValue() == null) {
                //En el caso de que no lo esté, deshabilitar el botón Search.
                btnSearch.setDisable(true);
                actualizarTabla();
            } else {
                //En el caso de que esté informado, habilitar el botón Search.
                btnSearch.setDisable(false);
            }
        }
        //El límite de caracteres del campo será de 300 caracteres. Si el usuario excede este límite no le permitirá escribir más.
        if (tfSearch.getText().trim().length() > 300) {
            tfSearch.setText(tfSearch.getText().substring(0, 300));
            new Alert(Alert.AlertType.ERROR, "The maximum lenght for the search field is 300 characters.", ButtonType.OK).showAndWait();
        }
    }

    /**
     * Método observable del cambio de selección en la tabla tbvUnits. El botón
     * de delete se desactivará o activará segun si hay algo seleccionado en la
     * tabla o no.
     *
     * @param observable La tabla que se está observando.
     * @param oldValue El objeto con el valor anterior en la tabla.
     * @param newValue El objeto con el valor actual en la tabla.
     */
    private void handleUnitTableSelectionChanged(ObservableValue observable, Object oldValue, Object newValue) {
        Unit unit = (Unit) newValue;
        if (unit != null) {
            btnDeleteUnit.setDisable(false);
        } else {
            btnDeleteUnit.setDisable(true);
        }
    }

    /**
     * El método del evento de pulsación del botón btnSearch. Determina el
     * recargo de la tabla dependiendo el criterio de busqueda y el valor a
     * buscar.
     *
     * @param event El evento de pulsación del botón.
     */
    @FXML
    public void handelSearchButtonAction(Event event) {
        LOGGER.info("Starting Search button action method");
        String subjectValue;
        String searchValue = null;
        try {
            //Comprobar el valor del combobox “cbSubjects”:
            subjectValue = (String) cbSubjects.getSelectionModel().getSelectedItem();
            if (subjectValue.equalsIgnoreCase("All subjects units")) {
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
                    Date date = Date.from(dpSearch.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                    clientsDataU = FXCollections.observableArrayList(clientU.findSubjectUnitsByDateInit(date, subjectValue));
                    tbvUnit.setItems((ObservableList) clientsDataU);
                    tbvUnit.refresh();
                } else if (searchValue.equalsIgnoreCase("Date End")) {
                    //Si el valor es Date End: Se usará el método “findSubjectUnitsByDateEnd” para rellenar la tabla pasandole el nombre de la subject seleccionada en la combobox y la fecha de la unidad escrita en el datePicker. 
                    Date date = Date.from(dpSearch.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
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
            LOGGER.log(Level.SEVERE, "There is no Unit with that " + searchValue, e.getMessage());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Error:" + e.getMessage(), ButtonType.OK).showAndWait();
            LOGGER.log(Level.SEVERE, "Error:" + e.getMessage(), e.getMessage());
        }
    }

    /**
     * El método del evento de pulsación del botón btnDeleteUnit. Pregunta al
     * usuario si quiere borrar la unit seleccionada en la tabla y si acepta lo
     * hace.
     *
     * @param event El evento de pulsación del botón.
     */
    @FXML
    public void handelDeleteButtonAction(Event event) {
        LOGGER.info("Starting delete unit method");
        try {
            //Recoger la unit seleccionada en la tabla.
            Unit unit = (Unit) tbvUnit.getSelectionModel().getSelectedItem();
            if (unit == null) {
                //Si no hay ninguna unit seleccionada avisar al usuario mediante una alerta.
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
            LOGGER.log(Level.SEVERE, "Error while deleting the Unit: ", e.getMessage());
            new Alert(Alert.AlertType.INFORMATION, "Error while deleting the Unit: " + e.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    /**
     * El método del evento de pulsación del botón btnCreateUnit. Crea una unit
     * con valores por defecto en la tabla y tiene en cuenta si existe alguna
     * unit en la misma subject con el mismo nombre antes de crearla.
     *
     * @param event El evento de pulsación del botón.
     */
    @FXML
    public void handelCreateButtonAction(Event event) {
        LOGGER.info("Starting create unit method.");
        try {
            //Se creará una nueva fila en la tabla con valores por defecto:
            Unit newUnit = new Unit();
            //Las columnas Name y Description estarán vacías.
            newUnit.setName("");
            newUnit.setDescription("");
            //El nombre no puede estar repetido, asi que comprobaremos que no hay otra unit en la misma subject con el nombre a vacio también.
            String subjectUnit = (String) cbSubjects.getSelectionModel().getSelectedItem();
            if (!subjectUnit.equalsIgnoreCase("All subjects units")) {
                //La columna Subject en caso de que una subject esté seleccionada en el combobox “cbSubjects”, se selecciona esta en este campo.
                clientsDataS = FXCollections.observableArrayList(clientS.findAllSubjects());
                for (int i = 0; i < clientsDataS.size(); i++) {
                    if (clientsDataS.get(i).getName().equalsIgnoreCase(subjectUnit)) {
                        newUnit.setSubject(clientsDataS.get(i));
                        i = clientsDataS.size();
                    }
                }
            } else {
                //Si no es el caso, será la primera posición del combobox “cbSubjects” la que se seleccione.
                List<String> list = cbSubjects.getItems();
                clientsDataS = FXCollections.observableArrayList(clientS.findAllSubjects());
                for (int i = 0; i < clientsDataS.size(); i++) {
                    subjectUnit = (String) list.get(1);
                    if (clientsDataS.get(i).getName().equalsIgnoreCase(subjectUnit)) {
                        newUnit.setSubject(clientsDataS.get(i));
                        i = clientsDataS.size();
                    }
                }
            }
            //Las columnas de Date Init y Date  End estarán cargadas con la fecha de hoy.
            newUnit.setDateInit(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            newUnit.setDateEnd(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            //La columna de Hours estará a 0.
            newUnit.setHours("0");
            //Comprobar si posible crear poque no habran dos units the una subject con el mismo nombre.
            Boolean create = true;
            if (!subjectUnit.equalsIgnoreCase("All subjects units")) {
                List<Unit> units = clientU.findSubjectUnits(subjectUnit);
                for (int i = 0; i < units.size(); i++) {
                    if (units.get(i).getName().equalsIgnoreCase("")) {
                        create = false;
                        i = units.size();
                    }
                }
            } else {
                List<String> list = cbSubjects.getItems();
                subjectUnit = list.get(1);
                List<Unit> units = clientU.findSubjectUnits(subjectUnit);
                for (int i = 0; i < units.size(); i++) {
                    if (units.get(i).getName().equalsIgnoreCase("")) {
                        create = false;
                        i = units.size();
                    }
                }
            }
            if (create) {
                //Se usará el método “createUnit” para crear una Unit con los valores por defecto que hemos estipulado pasandoselos en un objeto de tipo Unit.
                clientU.createUnit(newUnit);
                //Si la operación se lleva a cabo sin errores, la fila recién creada se mostrará en la tabla.
                new Alert(Alert.AlertType.INFORMATION, "Unit added successfully at " + subjectUnit + " subject", ButtonType.OK).showAndWait();
                actualizarTabla();
            } else {
                new Alert(Alert.AlertType.INFORMATION, "There is already a unit at " + subjectUnit + " subject without name, please change it before creating a new one on " + subjectUnit + " subject.", ButtonType.OK).showAndWait();
            }

            //Si se produce algún error, se le mostrará al usuario una alerta con el error y se cancelará la creación de la asignatura.
        } catch (FindErrorException | CreateErrorException ex) {
            Logger.getLogger("There was a problem while creating the unit").log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.INFORMATION, "There was a problem while creating the unit", ButtonType.OK).showAndWait();
        }
    }

    /**
     * El método del evento de pulsación del botón btnPrint. Crea un reporte con
     * los valores de la tabla y lo muestra.
     *
     * @param event El evento de pulsación del botón.
     */
    @FXML
    public void handelPrintButtonAction(Event event) {
        /*Se inicia la acción de impresión de los datos que tenga la tabla de Units en ese momento con todas las columnas de la misma.
         */
        try {
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/UnitReport.jrxml"));
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((List<Unit>) this.tbvUnit.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
            Logger.getLogger("There was a problem while printing the units: " + e.getMessage()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * El método del evento de pulsación de la x. Cierra la ventana pidiendo
     * antes confirmación del usuario.
     *
     * @param event El evento de pulsación en la x de la ventana.
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

    /**
     * Método para cargar la tabla dependiendo del valor de la combo cbSubjects
     * o para actualizarla despues de crear, borrar o modificar una unit.
     *
     */
    private void actualizarTabla() {
        try {
            String comboValue = (String) cbSubjects.getSelectionModel().getSelectedItem();
            if (comboValue.equalsIgnoreCase("All subjects units")) {
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
                clientsDataU = FXCollections.observableArrayList(clientU.findSubjectUnits(comboValue));
                tbvUnit.setItems((ObservableList) clientsDataU);
                tbvUnit.refresh();

            }
        } catch (FindErrorException ex) {
            Logger.getLogger("Error while recharging the table").log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método del evento de edición de celda de la columna tbcName. Comprobar
     * que el nueo nombre no se pasa del limite y que no existen dos unidades un
     * una subject con el mismo nombre y luego actualiza el campo.
     *
     * @param event El evento de edición de la celda.
     */
    @FXML
    private void onEditCommitColumnName(TableColumn.CellEditEvent<Unit, String> event) {
        LOGGER.info("Starting name editing cell");
        Unit unit = tbvUnit.getSelectionModel().getSelectedItem();
        //Si se pasa del límite: no le permitirá seguir escribiendo.
        String name = event.getNewValue();
        String subjectUnit = unit.getSubject().toString();
        try {
            //Si se presiona la tecla ENTER: Se verificará que el nombre ingresado en la celda no esté ya registrado como nombre de la subject the la unit editada ni se pase de 100 caracteres:
            clientsDataU = FXCollections.observableArrayList(clientU.findSubjectUnits(subjectUnit));
            Boolean modify = true;
            for (int i = 0; i < clientsDataU.size(); i++) {
                if (clientsDataU.get(i).getName().equalsIgnoreCase(name)) {
                    //Si el nombre ya existe: se lanzará la excepción “UnitNameExistException” y se notificará al usuario a través de una alerta pidiéndole que cambie el nombre porque ese ya existe como nombre de otra unidad.
                    modify = false;
                    i = clientsDataU.size();
                    tbvUnit.refresh();
                    throw new UnitNameExistException();
                }
            }
            //Si se pasa del límite: le saldra una alerta al usuario con el limite de caracteres
            if (name.length() > 100) {
                modify = false;
                new Alert(Alert.AlertType.ERROR, "Unit name length must be lower than 100 characters, please change it", ButtonType.OK).showAndWait();
            }

            if (modify) {
                //Si los dos criterios se cumplen: Se usará el método “updateUnit” para guardar la edición en la base de datos, pasandole la unit seleccionada en un objeto de tipo unit.
                unit.setName(name);
                clientU.updateUnit(unit);
            }
            //Si no se produce ningún error, se refrescará la tabla.
            actualizarTabla();
            //Si se produce algún error, se le mostrará al usuario una alerta con el error  y se cancelará la edición.
            //Si se realiza doble clic en la celda: Se ingresará al modo de edición y si se presiona la tecla ESC o se hace clic en cualquier lugar fuera de la celda, se cancelará la edición.

        } catch (UnitNameExistException ex) {
            new Alert(Alert.AlertType.ERROR, "Unit name already exists in " + subjectUnit + " subject.", ButtonType.OK).showAndWait();
            Logger.getLogger("Unit name already exists in " + subjectUnit + " subject.").log(Level.SEVERE, null, ex);
        } catch (FindErrorException | UpdateErrorException ex) {
            Logger.getLogger("Error while updating name from the unit").log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error while updating name from the unit", ButtonType.OK).showAndWait();
            
        }
    }

    /**
     * Método del evento de edición de celda de la columna tbcSubject. Comprobar
     * que en la subject seleccionada no existe una unidad con el nombre de la
     * unidad a editar antes de editarla.
     *
     * @param event El evento de edición de la celda.
     */
    @FXML
    private void onEditCommitColumnSubject(TableColumn.CellEditEvent<Unit, Subject> event) {
        LOGGER.info("Starting subject editing cell.");
        //Si se realiza doble clic en la celda: Se ingresará al modo de edición y si se presiona la tecla ESC o se hace clic en cualquier lugar fuera de la celda, se cancelará la edición.
        //Se mostrará un combobox con las opciones del combobox “cbSubjects”.
        //Si se presiona la tecla ENTER: Se verificará que haya algún valor seleccionado y que ese valor sea diferente del existente.
        Unit unit = tbvUnit.getSelectionModel().getSelectedItem();
        String name = unit.getName();
        try {
            //Se verificará que en la subject seleccionada no exista un mismo nombre de unidad
            clientsDataU = FXCollections.observableArrayList(clientU.findSubjectUnits(event.getNewValue().toString()));
            Boolean modify = true;
            for (int i = 0; i < clientsDataU.size(); i++) {
                if (clientsDataU.get(i).getName().equalsIgnoreCase(name)) {
                    //Si el nombre ya existe: se lanzará la excepción “UnitNameExistException” y se notificará al usuario a través de una alerta pidiéndole que cambie el nombre porque ese ya existe como nombre de otra unidad.
                    modify = false;
                    i = clientsDataU.size();
                    tbvUnit.refresh();
                    throw new UnitNameExistException();
                }
            }
            if (!event.getNewValue().getName().equalsIgnoreCase(event.getOldValue().getName())) {
                //Si se cumple: Se usará el método “updateUnit” para guardar la edición en la base de datos, pasandole la unit seleccionada con el cambio en un objeto de tipo unit.
                if (modify) {
                    unit.setSubject(event.getNewValue());
                    clientU.updateUnit(unit);
                }
                //Si es el mismo valor: no se requerirá ninguna acción ya que no habrá edición alguna.
            }
            //Si se produce algún error, se le mostrará al usuario una alerta con el error  y se cancelará la edición.
            //Si no se produce ningún error, se refrescará la tabla.
            actualizarTabla();
        } catch (UnitNameExistException ex) {
            new Alert(Alert.AlertType.ERROR, "Unit name already exists in " + event.getNewValue().toString() + " subject.", ButtonType.OK).showAndWait();
            Logger.getLogger("Unit name already exists in " + event.getNewValue().toString() + " subject.").log(Level.SEVERE, null, ex);
        } catch (FindErrorException | UpdateErrorException ex) {
            new Alert(Alert.AlertType.ERROR, "Error while updating Subject from the unit: " + unit.getName(), ButtonType.OK).showAndWait();
            Logger.getLogger("Error while updating Subject from the unit: " + unit.getName()).log(Level.SEVERE, null, ex);
            event.consume();
            tbvUnit.refresh();
        }
    }

    /**
     * Método del evento de edición de celda de la columna tbcDescription.
     * Comprobar que la nueva descripcion no se pasa del limite y luego
     * actualiza el campo.
     *
     * @param event El evento de edición de la celda.
     */
    @FXML
    private void onEditCommitColumnDescription(TableColumn.CellEditEvent<Unit, String> event) {
        LOGGER.info("Starting Description editing cell.");
        Boolean modify = true;
        Unit unit = tbvUnit.getSelectionModel().getSelectedItem();
        String desc = event.getNewValue();
        //Si se realiza doble clic en la celda: Se ingresará al modo de edición y si se presiona la tecla ESC o se hace clic en cualquier lugar fuera de la celda, se cancelará la edición.
        try {
            //Si se pasa del límite: le saldra una alerta al usuario con el limite de caracteres
            if (desc.length() > 100) {
                modify = false;
                new Alert(Alert.AlertType.ERROR, "Unit description length must be lower than 100 characters, please change it", ButtonType.OK).showAndWait();
            }

            //Si se cumplen: Se usará el método “updateUnit” para guardar la edición en la base de datos, pasandole la unit seleccionada en un objeto de tipo unit.
            if (modify) {
                unit.setDescription(desc);
                clientU.updateUnit(unit);
            }
            //Si no se produce ningún error, se refrescará la tabla.
            //Si se produce algún error, se le mostrará al usuario una alerta con el error  y se cancelará la edición.
        } catch (UpdateErrorException ex) {
            //Si se produce algún error, se le mostrará al usuario una alerta con el error.  
            Logger.getLogger("Error while updating description from the unit: " + unit.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error while updating description from the unit: " + unit.getName(), ButtonType.OK).showAndWait();
            //Se cancelará la edición.
            event.consume();
            tbvUnit.refresh();
        }
    }

    /**
     * Método del evento de edición de celda de la columna tbcDateInit.
     * Comprueba que la date end este en los parametros contemplados del config
     * file y luego actualiza la fecha.
     *
     * @param event El evento de edición de la celda.
     */
    @FXML
    private void onEditCommitColumnDateInit(TableColumn.CellEditEvent<Unit, Date> event) {
        LOGGER.info("Starting dateInit editing cell");
        //Comprobar en el archivo de configuración cual es el periodo en el que se pueden añadir nuevas units.
        String startDateConfig = ResourceBundle.getBundle("config.config").getString("STARTDATE");
        String endDateConfig = ResourceBundle.getBundle("config.config").getString("ENDDATE");
        LocalDate startDate = LocalDate.parse(startDateConfig);
        LocalDate endDate = LocalDate.parse(endDateConfig);
        Unit unit = (Unit) event.getTableView().getItems().get(event.getTablePosition().getRow());
        try {
            //Coger la fecha para hacer las comprobaciones
            Date dateInit = event.getNewValue();
            LocalDate dateInitAnalyze = dateInit.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            //Se validará que ambas fechas estén comprendidas dentro de un período de tiempo. 
            // El periodo de tiempo válido será definido en un archivo de configuración y podrá ser modificado. 
            if (dateInitAnalyze.isBefore(endDate) && dateInitAnalyze.isAfter(startDate)) {
                unit.setDateInit(dateInit);
                //Se usará el método “updateUnit” para guardar la edición en la base de datos, pasandole la unit seleccionada  con el cambio en un objeto de tipo unit.
                clientU.updateUnit(unit);
            } else {
                //Si las fechas no se encuentran dentro de ese periodo, se lanzará la excepción WrongDateException y se notificará al usuario mediante una alerta con el siguiente mensaje de error: 
                //'Our application only stores information from year X to year Y. Please enter a date between these two years.'

                throw new WrongDateFormatException();
            }
        } catch (WrongDateFormatException ex) {
            tbvUnit.refresh();
            Logger.getLogger("Our application only stores information from year " + startDate.getYear() + " to year " + endDate.getYear()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.INFORMATION, "Our application only stores information from year " + startDate.getYear() + " to year " + endDate.getYear(), ButtonType.OK).showAndWait();

        } catch (UpdateErrorException ex) {
            //Si se produce algún error, se le mostrará al usuario una alerta con el error.  
            Logger.getLogger("Error while updating date init from the unit: " + unit.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error while updating date init from the unit: " + unit.getName(), ButtonType.OK).showAndWait();
            tbvUnit.refresh();
            //Se cancelará la edición.
            event.consume();
        }
    }

    /**
     * Método del evento de edición de celda de la columna tbcDateEnd. Comprueba
     * que la date end este en los parametros contemplados del config file y
     * luego actualiza la fecha.
     *
     * @param event El evento de edición de la celda.
     */
    @FXML
    private void onEditCommitColumnDateEnd(TableColumn.CellEditEvent<Unit, Date> event) {
        LOGGER.info("Starting dateEnd editing cell");
        //Comprobar en el archivo de configuración cual es el periodo en el que se pueden añadir nuevas units.
        String startDateConfig = ResourceBundle.getBundle("config.config").getString("STARTDATE");
        String endDateConfig = ResourceBundle.getBundle("config.config").getString("ENDDATE");
        LocalDate startDate = LocalDate.parse(startDateConfig);
        LocalDate endDate = LocalDate.parse(endDateConfig);
        Boolean modify;

        //Coger la fecha para hacer las comprobaciones
        Unit unit = (Unit) event.getTableView().getItems().get(event.getTablePosition().getRow());
        Date dateInit = unit.getDateInit();
        LocalDate dateEndAnalyze = event.getNewValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateInitAnalyze = dateInit.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        try {
            //Se validará que ambas fechas estén comprendidas dentro de un período de tiempo. 
            // El periodo de tiempo válido será definido en un archivo de configuración y podrá ser modificado. 
            if (dateEndAnalyze.isBefore(endDate) && dateEndAnalyze.isAfter(startDate)) {
                modify = true;
            } else {
                //Si las fechas no se encuentran dentro de ese periodo, se lanzará la excepción WrongDateException y se notificará al usuario mediante una alerta con el siguiente mensaje de error: 
                //'Our application only stores information from year X to year Y. Please enter a date between these two years.'
                modify = false;
                throw new WrongDateFormatException();
            }
            //Se validará que la fecha de inicio sea anterior a la fecha fín, si no, 
            //se lanzará la excepción WrongDateFormatException con el siguiente mensaje: 'The end date must be later than the start date of the subject.
            if (dateEndAnalyze.isBefore(dateInitAnalyze)) {
                modify = false;
                try {
                    throw new WrongDateFormatException();
                } catch (WrongDateFormatException ex) {
                    tbvUnit.refresh();
                    new Alert(Alert.AlertType.INFORMATION, "The date end must be later than the date init of the subject.", ButtonType.OK).showAndWait();
                }
            }
            if (modify) {
                unit.setDateEnd(event.getNewValue());
                //Tras la validación y confirmación de que la información es correcta, se llamará a la factoria SubjectFactory para obtener una implematación de la interfaz SubjectManager 
                //y llamar al método updateSubject, pasando como parámetro un objeto Subject con la información.
                clientU.updateUnit(tbvUnit.getSelectionModel().getSelectedItem());
            }
        } catch (UpdateErrorException ex) {
            //Si se produce algún error, se le mostrará al usuario una alerta con el error.  
            Logger.getLogger("Error while updating date end from the unit: " + unit.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, "Error while updating date end from the unit: " + unit.getName(), ButtonType.OK).showAndWait();
            //Se cancelará la edición.
            event.consume();
            tbvUnit.refresh();
        } catch (WrongDateFormatException ex) {
            Logger.getLogger("Our application only stores information from year " + startDate.getYear() + " to year " + endDate.getYear()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.INFORMATION, "Our application only stores information from year " + startDate.getYear() + " to year " + endDate.getYear(), ButtonType.OK).showAndWait();
        }

    }

    /**
     * Método del evento de edición de celda de la columna tbcHours. Comprueba
     * que unicamente haya numeros para poder actualizar el valor.
     *
     * @param event El evento de edición de la celda.
     */
    @FXML
    private void onEditCommitColumnHours(TableColumn.CellEditEvent<Unit, String> event) {
        LOGGER.info("Starting hours editing cell");

        //Si se realiza doble clic en la celda: Se ingresará al modo de edición y si se presiona la tecla ESC o se hace clic en cualquier lugar fuera de la celda, se cancelará la edición.
        //Si se presiona la tecla ENTER: Se verificará que la información ingresada en la celda consista únicamente de números enteros. 
        Unit unit = tbvUnit.getSelectionModel().getSelectedItem();
        String intNew = event.getNewValue();
        Boolean modify = true;
        String regexNumbers = "^[0-9]+$";
        try {
            if (!event.getNewValue().matches(regexNumbers)) {
                try {
                    modify = false;
                    //En caso de que no se cumpla: Se lanzará la excepción “WrongNumberFormatException” y se notificará al usuario a través de una alerta con el siguiente mensaje de error: “Invalid hours column value, please enter only integer numbers.”
                    throw new WrongNumberFormatException();
                } catch (WrongNumberFormatException ex) {
                    new Alert(Alert.AlertType.ERROR, "Invalid input " + intNew + ". Please enter only numbers.", ButtonType.OK).showAndWait();
                    tbvUnit.refresh();
                    LOGGER.log(Level.SEVERE, "Invalid imput in hours cell: ", ex.getMessage());
                }
            }
            if (modify) {
                //En caso de que se cumpla: Se usará el método “updateUnit” para guardar la edición en la base de datos, pasandole la unit seleccionada  con el cambio en un objeto de tipo unit.
                unit.setHours(intNew);
                clientU.updateUnit(unit);
            }
        } catch (UpdateErrorException ex) {
            //Si se produce algún error, se le mostrará al usuario una alerta con el error.  
            //Si se produce algún error, se le mostrará al usuario una alerta con el error  y se cancelará la edición.
            new Alert(Alert.AlertType.ERROR, "Error while updating hours end from the unit: " + unit.getName(), ButtonType.OK).showAndWait();
            LOGGER.log(Level.SEVERE, "Error while updating hours from the unit: " + unit.getName(), ex.getMessage());
            //Se cancelará la edición.
            event.consume();
            tbvUnit.refresh();
        }
    }

    /**
     * El método para establecer el stage en el que se va a abrir la ventana.
     *
     * @param stage El stage que utilizará la ventana.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * El método para cargar la ventana con la subject de la que se quieren ver
     * las units. El objeto viene de la ventana de subjects y se selecciona en
     * la combobox cbSubjects.
     *
     * @param subject La subject de la que se quieren ver unidades.
     */
    public void setCurrentSubject(Subject subject) {
        //Se guarda en modo de texto la subject obtenida.
        String subjectName = subject.toString();
        //Se selecciona en la combobox cbSubjects.
        cbSubjects.getSelectionModel().select(subjectName);
    }
}
