package view.exercise;

import exceptions.CreateErrorException;
import exceptions.DeleteErrorException;
import exceptions.ExerciseErrorException;
import exceptions.FindErrorException;
import exceptions.UpdateErrorException;
import factories.ExerciseFactory;
import factories.SendFileFactory;
import factories.UnitFactory;
import interfaces.ExerciseInterface;
import interfaces.SendFileInterface;
import interfaces.UnitInterface;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Exercise;
import models.LevelType;
import models.Unit;
import models.User;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Controller class for exercises management view. It contains event handlers
 * and initialization code for the view defined in exercise.fmxl file.
 *
 * @author Leire
 */
public class ExerciseController {

    private static final Logger LOGGER = Logger.getLogger("Exercise view.");
    private Stage stage;
    private User user;
    private Exercise exercise;
    private Unit unit;
    /**
     * Exercise table data model.
     */
    private ObservableList<Exercise> exerciseData;
    /**
     * Unit table data model.
     */
    private ObservableList<Unit> unitData;

    //Interfaces.
    private ExerciseInterface exerciseInterface;
    private UnitInterface unitInterface;
    private SendFileInterface fileInterface;

    private static final String NUMBERS_REGEX = "^[0-9]*$";
    private List<Exercise> byUnit = new ArrayList<>();

    /**
     * Exercise data table view.
     *
     */
    @FXML
    private TableView<Exercise> tvExercise;

    /**
     * Exercise number data table column. Exercise description data table
     * column. Exercise file data table column. Exercise file solution data
     * table column. Exercise deadline data table column. Exercise hours data
     * table column.
     *
     */
    @FXML
    private TableColumn tcNumber, tcDescription, tcFile, tcFileSolution, tcDeadline, tcHours;

    /**
     * Exercise unit data table column.
     *
     */
    @FXML
    private TableColumn<Exercise, Unit> tcUnit;

    /**
     * Exercise level type data table column.
     */
    @FXML
    private TableColumn<Exercise, LevelType> tcLevelType;

    /**
     * Exercise search UI text field. Exercise number UI text field. Exercise
     * description UI text field. Exercise hours UI text field.
     *
     */
    @FXML
    private TextField tfSearch, tfNumber, tfDescription, tfHours;

    /**
     * Unit combo box for the search. Level type combo box for the search. Unit
     * combo box for the create and modify. Level type combo box for the create
     * and modify.
     *
     */
    @FXML
    private ComboBox cbUnitSearch, cbSearchType, cbUnitCreate, cbLevelTypeCreate;

    /**
     * Exercise deadline date picker.
     *
     */
    @FXML
    private DatePicker dpDeadline;

    /**
     * Search exercise data button. Delete exercise data button. Modify exercise
     * data button. Create exercise data button. Print exercise data button.
     * Send a file button. Send a file with the solution button. Receive a file
     * button. Receive a file solution button.
     *
     */
    @FXML
    private Button btmSearch, btmDelete, btmModify, btmCreate, btmPrint, btmFileSend, btmFileSolutionSend, btmFileReceive, btmFileSolutionReceive;
    /**
     *
     */

    @FXML
    private Label lblErrorCreateModify;

    /**
     * Method for initializing Exercise Stage.
     *
     * @param root The Parent object representing root node of view graph.
     * @param loggedUser The user that is logged to the application.
     * @throws exceptions.ExerciseErrorException Thrown when any error or
     * exception occurs.
     * @throws exceptions.FindErrorException Thrown when any error or exception
     * occurs.
     */
    public void initialize(Parent root, User loggedUser) throws ExerciseErrorException, FindErrorException {
        Scene scene = new Scene(root);
        LOGGER.info("Initializing the exercise window.");
        this.user = loggedUser;
        exerciseInterface = ExerciseFactory.getModel();
        unitInterface = UnitFactory.getModel();
        fileInterface = SendFileFactory.getModel();
        //El nombre de la ventana será “Exercise”.
        stage.setTitle("Exercise");

        //Se añadirá a la ventana el icono de una estrella.
        stage.getIcons().add(new Image("resources/Logo.jpg"));

        //Ventana no redimensionable.
        stage.setResizable(false);

        //Los botones “btmModify”, “btmCreate”,
        //“btmFileSend”, "btmFileSolutionSend", "btmFileReceive", 
        //"btmFileSolutionReceive" y “btmDelete” estarán deshabilitados, y el botón
        //”btmPrint” estará habilitado. El campo de texto "tfSearch" se deshabilitará.
        this.btmModify.setDisable(true);
        this.btmCreate.setDisable(true);
        this.btmDelete.setDisable(true);
        btmFileSend.setDisable(true);
        btmFileSolutionSend.setDisable(true);
        btmFileReceive.setDisable(true);
        btmFileSolutionReceive.setDisable(true);
        tfSearch.setDisable(true);

        //Si el usuario que ha entrado a la aplicación es de tipo Student, 
        //los campos “tfSearch”, “tfNumber”, “tfDescription”,
        //“btmFileSend”, “btmFileSolutionSend”, "btmFileReceive", 
        //"btmFileSolutionReceive".
        //El date picker "dpDeadline" y los combobox “cbUnitCreate” y 
        //“cbLevelTypeCreate” estarán deshabilitados.
        if (loggedUser.getUser_type().equalsIgnoreCase("Student")) {
            this.btmSearch.setDisable(true);
            tfSearch.setDisable(false);
            this.tfNumber.setDisable(true);
            this.tfDescription.setDisable(true);
            this.tfHours.setDisable(true);
            this.btmFileSend.setDisable(true);
            this.btmFileSolutionSend.setDisable(true);
            this.btmFileReceive.setDisable(true);
            this.btmFileSolutionReceive.setDisable(true);
            this.dpDeadline.setDisable(true);
            this.cbUnitCreate.setDisable(true);
            this.cbLevelTypeCreate.setDisable(true);
        }

        //Se rellenará el combobox “cbUnitCreate” con una lista de todas las 
        //unidades de las asignaturas en las que el usuario Teacher esté 
        //matriculado. Se obtendrá la información de las unidades llamando al 
        //método findUnitsFromTeacherSubjects de la interfaz UnitInterface.
        if (loggedUser.getUser_type().equalsIgnoreCase("Teacher")) {
            try {
                unitData = FXCollections.observableArrayList(unitInterface.findUnitsFromTeacherSubjects(loggedUser.getId().toString()));
                if (unitData.isEmpty()) {
                    //Si el método no devuelve nada se rellena con el texto ”No unit found” y lo selecciona.
                    this.cbUnitCreate.getItems().add("No unit found");
                } else {
                    List<String> unitNames = new ArrayList<>();
                    for (int i = 0; i < unitData.size(); i++) {
                        String unitName = unitData.get(i).getName();
                        unitNames.add(unitName);
                    }
                    this.cbUnitCreate.getItems().addAll(unitNames);
                    cbUnitCreate.getSelectionModel().selectFirst();
                }
            } catch (FindErrorException e) {
                LOGGER.log(Level.SEVERE, "Error searching for teacher unit");
            }
        } else {
            try {
                this.cbUnitCreate.setDisable(true);

            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error searching for student unit");
            }
        }

        //Se rellenará el combobox “cbLevelTypeCreate” con una lista de los 
        //tres tipos de nivel. Se seleccionará el primero.
        this.cbLevelTypeCreate.getItems().addAll(LevelType.values());
        cbLevelTypeCreate.getSelectionModel().selectFirst();

        //Se rellenará el combobox “cbUnitSearch” con una lista de unidades de 
        //las asignaturas en las cuales esté matriculado el usuario. 
        //El usuario puede ser de dos tipos:
        if (loggedUser.getUser_type().equalsIgnoreCase("Teacher")) {
            try {
                //Si el usuario es de tipo Teacher se obtendrá la información de las 
                //unidades llamando al método findUnitsFromTeacherSubjects de la 
                //interfaz UnitInterface para rellenar el combobox.
                unitData = FXCollections.observableArrayList(unitInterface.findUnitsFromTeacherSubjects(loggedUser.getId().toString()));
                if (unitData.isEmpty()) {
                    //Si el método no devuelve nada se rellena con el texto ”No unit found” y lo selecciona.
                    this.cbUnitSearch.getItems().add("No unit found");
                    cbUnitSearch.getSelectionModel().selectFirst();
                } else {
                    List<String> unitNames = new ArrayList<>();
                    for (int i = 0; i < unitData.size(); i++) {
                        String unitName = unitData.get(i).getName();
                        unitNames.add(unitName);
                    }
                    this.cbUnitSearch.getItems().addAll(unitNames);
                    cbUnitSearch.getSelectionModel().selectFirst();
                }
            } catch (FindErrorException e) {
                LOGGER.log(Level.SEVERE, "Error searching for teacher unit");
            }
        } else {
            try {
                //Si el usuario es de tipo Student se obtendrá la información de las 
                //unidades llamando al método findUnitsFromStudentSubjects de la interfaz 
                //UnitInterface para rellenar el combobox.
                unitData = FXCollections.observableArrayList(unitInterface.findUnitsFromStudentSubjects(loggedUser.getId().toString()));
                if (unitData.isEmpty()) {
                    //Si el método no devuelve nada se rellena con el texto ”No unit found” y lo selecciona.
                    this.cbUnitSearch.getItems().add("No unit found");
                    cbUnitSearch.getSelectionModel().selectFirst();
                } else {
                    List<String> unitNames = new ArrayList<>();
                    for (int i = 0; i < unitData.size(); i++) {
                        String unitName = unitData.get(i).getName();
                        unitNames.add(unitName);
                    }
                    this.cbUnitSearch.getItems().addAll(unitNames);
                    this.cbUnitSearch.getSelectionModel().selectFirst();
                }
            } catch (FindErrorException e) {
                LOGGER.log(Level.SEVERE, "Error searching for student unit");
            }
        }

        //Se rellenará el combobox “cbSearchType” con los tipos de búsqueda: 
        //Number y Level type.
        //Si el usuario es de tipo Teacher también se añadirá el tipo de 
        //búsqueda: All.
        if (loggedUser.getUser_type().equalsIgnoreCase("Teacher")) {
            this.cbSearchType.getItems().addAll("All", "Level type", "Number");
            //El combobox “cbSearchType” tendrá la opción de All por defecto.
            this.cbSearchType.setValue("All");
        } else {
            this.cbSearchType.getItems().addAll("Level type", "Number");
            this.cbSearchType.setValue("Level type");
        }

        //La tabla mostrará los atributos: Unit, Number, Description, Level 
        //type, File, File solution, Deadline y Hours.
        this.tcUnit.setCellValueFactory(
                new PropertyValueFactory<>("unit")
        );
        this.tcNumber.setCellValueFactory(
                new PropertyValueFactory<>("number")
        );
        this.tcDescription.setCellValueFactory(
                new PropertyValueFactory<>("description")
        );
        this.tcLevelType.setCellValueFactory(
                new PropertyValueFactory<>("levelType")
        );
        this.tcFile.setCellValueFactory(
                new PropertyValueFactory<>("file")
        );
        this.tcFileSolution.setCellValueFactory(
                new PropertyValueFactory<>("fileSolution")
        );
        this.tcDeadline.setCellValueFactory(
                new PropertyValueFactory<>("deadline")
        );
        //La columna “tcDeadline” se mostrará con un patrón formateado de 
        //acuerdo a la configuración del sistema.
        this.tcDeadline.setCellFactory(column -> {
            TableCell<Exercise, Date> cell = new TableCell<Exercise, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        this.setText(format.format(item));
                    }
                }
            };

            return cell;
        });
        this.tcHours.setCellValueFactory(
                new PropertyValueFactory<>("hours")
        );
        String unitName = this.cbUnitSearch.getSelectionModel().getSelectedItem().toString();
        //Crear una obsevable list para la tabla de exercises.
        exerciseData = FXCollections.observableArrayList(exerciseInterface.findExercisesByUnitName(unitName));
        //Añadir modelos a la tabla.
        this.tvExercise.setItems(exerciseData);

        //Add property change listeners for controls 
        this.tvExercise.getSelectionModel().selectedItemProperty()
                .addListener(this::handleExerciseTableSelectionChanged);

        this.tfSearch.textProperty().addListener(this::handleFieldsTextChange);
        this.tfNumber.textProperty().addListener(this::handleFieldsTextChange);
        this.tfHours.textProperty().addListener(this::handleFieldsTextChange);
        this.tfDescription.textProperty().addListener(this::handleFieldsTextChange);

        stage.setOnCloseRequest(this::handleOnActionExit);

        btmFileSend.setOnAction(this::handleOnActionFileSend);
        btmFileSolutionSend.setOnAction(this::handleOnActionFileSolutionSend);
        btmFileReceive.setOnAction(this::handleOnActionFileReceive);
        btmFileSolutionReceive.setOnAction(this::handleOnActionFileSolutionReceive);

        cbSearchType.getSelectionModel().selectedItemProperty().addListener((event) -> this.textChangeSearch(KeyEvent.KEY_TYPED));

        this.tfSearch.textProperty().addListener((event) -> this.textChangeSearch(KeyEvent.KEY_TYPED));

        this.tfNumber.textProperty().addListener((event) -> this.textOnlyNumbers(KeyEvent.KEY_TYPED));
        this.tfHours.textProperty().addListener((event) -> this.textOnlyNumbers(KeyEvent.KEY_TYPED));

        this.tfNumber.textProperty().addListener((event) -> this.textChangeCreate(KeyEvent.KEY_TYPED));
        this.tfHours.textProperty().addListener((event) -> this.textChangeCreate(KeyEvent.KEY_TYPED));
        this.tfDescription.textProperty().addListener((event) -> this.textChangeCreate(KeyEvent.KEY_TYPED));

        stage.setScene(scene);
        //Mostrar la ventana. 
        stage.show();
    }

    /**
     * A key event for the "tfSearch" text field. Depending on the search
     * method, the "tfSearch" text field and the "btmSearch" button will be
     * enabled or disabled.
     *
     * @param KEY_TYPED to represent keyboard actions, that is, pressing keys.
     */
    private void textChangeSearch(int KEY_TYPED) {
        String searchValue = cbSearchType.getSelectionModel().getSelectedItem().toString();
        //Si se busca por "All", se deshabilitará el campo de texto "tfSearch" 
        //y se habilitará el botón "btmSearch".
        if (searchValue.equalsIgnoreCase("All")) {
            tfSearch.setDisable(true);
            this.btmSearch.setDisable(false);
            //Si no se busca por "All" se habilitará el campo de texto "tfSearch"
            //y se habilitará el botón "btmSearch" si el campo no está vacío.
        } else if (!searchValue.equalsIgnoreCase("All")) {
            tfSearch.setDisable(false);
            if (!this.tfSearch.getText().trim().isEmpty()) {
                this.btmSearch.setDisable(false);
            }
            if (this.tfSearch.getText().trim().isEmpty()) {
                this.btmSearch.setDisable(true);
            }
        }
    }

    /**
     * Text change event handler for the table selection. Passes the table data
     * to the corresponding text fields and enables or disables the buttons
     * depending on whether a row is selected.
     *
     * @param observable The property being observed: SelectedItem Property
     * @param oldValue Old UserBean value for the property.
     * @param newValue New UserBean value for the property.
     */
    private void handleExerciseTableSelectionChanged(ObservableValue observable,
            Object oldValue,
            Object newValue) {
        try {
            //Si hay una fila seleccionada, mueve los datos de la fila a los 
            //campos correspondientes en la ventana y habilita los botones 
            //"btmModify", "btmDelete", "btmFileSend", "btmFileSolutionSend", 
            //"btmFileReceive" y "btmFileSolutionReceive".
            Exercise exerciseModify = (Exercise) newValue;
            if (newValue != null) {
                cbUnitCreate.getSelectionModel().select(exerciseModify.getUnit().toString());
                tfNumber.setText(exerciseModify.getNumber());
                tfHours.setText(exerciseModify.getHours());
                cbLevelTypeCreate.getSelectionModel().select(exerciseModify.getLevelType());
                dpDeadline.setValue(exerciseModify.getDeadline().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                tfDescription.setText(exerciseModify.getDescription());
                this.btmModify.setDisable(false);
                this.btmDelete.setDisable(false);
                this.btmFileSend.setDisable(false);
                this.btmFileSolutionSend.setDisable(false);
                this.btmFileReceive.setDisable(false);
                this.btmFileSolutionReceive.setDisable(false);
                //Si el usuario es de tipo Student, los botones anteriormente
                //mencionados se deshabilitarán, menos los botones 
                //"btmFileReceive" y "btmFileSolutionReceive"
                if (user.getUser_type().equalsIgnoreCase("Student")) {
                    this.btmModify.setDisable(true);
                    this.btmDelete.setDisable(true);
                    this.btmCreate.setDisable(true);
                    this.btmFileSend.setDisable(true);
                    this.btmFileSolutionSend.setDisable(true);
                }
            } else {
                //Si no hay una fila seleccionada, limpia los campos de la ventana
                //y deshabilitar los botones.
                tfNumber.setText("");
                tfHours.setText("");
                dpDeadline.setValue(null);
                tfDescription.setText("");
                this.btmModify.setDisable(true);
                this.btmDelete.setDisable(true);
                this.btmFileSend.setDisable(true);
                this.btmFileSolutionSend.setDisable(true);
                this.btmFileReceive.setDisable(true);
                this.btmFileSolutionReceive.setDisable(true);
            }
        } catch (Exception ex) {
            showErrorAlert("Error selection");
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

    /**
     * A key event for the "tfNumber", "tfHours" and "tfDescription" text
     * fields. If any of the fields are empty, the "btmCreate" button will be
     * disabled until all fields are reported.
     *
     * @param KEY_TYPED to represent keyboard actions, that is, pressing keys.
     */
    private void textChangeCreate(int KEY_TYPED) {
        if (!this.tfNumber.getText().trim().isEmpty() && !this.tfHours.getText().trim().isEmpty() && !this.tfDescription.getText().trim().isEmpty()) {
            this.btmCreate.setDisable(false);
        }
        if (!this.tfNumber.getText().matches(NUMBERS_REGEX) || !this.tfHours.getText().matches(NUMBERS_REGEX) || this.tfNumber.getText().trim().isEmpty() || this.tfHours.getText().trim().isEmpty() || this.tfDescription.getText().trim().isEmpty()) {
            this.btmCreate.setDisable(true);
            this.btmModify.setDisable(true);
        }
    }

    /**
     * A key event for the "tfNumber" and the "tfHours" text fields. If either
     * of these two fields is reported with a non-numeric character, the
     * "btmCreate" and "btmModify" buttons will be disabled. Informational text
     * will also be added to the "lblErrorCreateModify" label. The label will
     * disappear and the "btmCreate" and "btmModify" buttons will be enabled
     * when the fields are reported with numerical characters only.
     *
     * @param KEY_TYPED to represent keyboard actions, that is, pressing keys.
     */
    private void textOnlyNumbers(int KEY_TYPED) {
        if (!this.tfNumber.getText().matches(NUMBERS_REGEX) || !this.tfHours.getText().matches(NUMBERS_REGEX)) {
            this.lblErrorCreateModify.setText("🛈 Only numbers are allowed on the number and the hours fields.");
            btmCreate.setDisable(true);
            btmModify.setDisable(true);
        } else {
            this.lblErrorCreateModify.setText(" ");
            btmModify.setDisable(false);
        }
    }

    /**
     * Text change event handler for search, number, hours and description
     * fields. If the fields exceed this limit, the user will not be allowed to
     * write more.
     *
     * @param observable The property being observed: TextProperty of TextField.
     * @param oldValue Old String value for the property.
     * @param newValue New String value for the property.
     */
    public void handleFieldsTextChange(ObservableValue observable,
            String oldValue,
            String newValue) {
        /**
         * El límite de caracteres de los campos será de 300 caracteres, menos
         * el campo de Description, que tendrá un límite de 600 caracteres. Si
         * se llega a este límite no se le dejará escribir más al usuario y le
         * saldrá un mensaje de color rojo en una label : “The character limit
         * has been exceeded.”.
         */
        if (this.tfSearch.getText().length() > 300) {
            tfSearch.setText(tfSearch.getText().substring(0, 300));
        }
        if (this.tfNumber.getText().length() > 300) {
            tfNumber.setText(tfNumber.getText().substring(0, 300));
        }
        if (this.tfHours.getText().length() > 300) {
            tfHours.setText(tfHours.getText().substring(0, 300));
        }
        if (this.tfDescription.getText().length() > 600) {
            tfDescription.setText(tfDescription.getText().substring(0, 600));
        }
    }

    /**
     * Action event handler for create button. It validates new exercise data,
     * send it to the business logic tier and updates exercise table view with
     * new exercise data.
     *
     * @param event The ActionEvent object for the event.
     * @throws CreateErrorException Thrown when any error or exception occurs
     * for create.
     */
    /*
    · El botón Create se habilitará si los campos de texto, “tfSearch”, “tfNumber”, 
    “tfDescription”, “tfHours”, los botones “btmFileSolution”, “btmFile” y las 
    combobox “cbUnitCreate” y “cbLevelTypeCreate”, están informados.
    · Los botones “btmFileSolution” y “btmFile” usarán el método sendFile de la interfaz sendFileInterface.
    · Se mostrará un mensaje de confirmación cuando el botón se pulse:
    · En caso afirmativo, se llamará al método create de la interfaz ExerciseInterface. 
    · Si todo ha ido correctamente se actualizará la tabla. Se limpiarán los campos de texto.
    · En caso negativo, se cerrará el mensaje de confirmación.
    · Si se produce algún error se le mostrará al usuario un mensaje de error con dicho 
    error y no se realizará ningún cambio.

     */
    @FXML
    private void handleCreateButtonAction(javafx.event.ActionEvent event) throws CreateErrorException {
        try {
            LOGGER.info("Creating user...");

            Exercise newExercise = new Exercise();

            String unitName = this.cbUnitCreate.getSelectionModel().getSelectedItem().toString();
            List<Unit> units = unitInterface.findAllUnits();
            Unit unit = new Unit();
            for (int i = 0; i < units.size(); i++) {
                if (units.get(i).getName().equalsIgnoreCase(unitName)) {
                    unit = units.get(i);
                }
            }
            newExercise.setUnit(unit);
            newExercise.setNumber(this.tfNumber.getText().trim());
            newExercise.setHours(this.tfHours.getText().trim());
            LocalDate datePicker = dpDeadline.getValue();
            Date date = Date.from(datePicker.atStartOfDay(ZoneId.systemDefault()).toInstant());
            newExercise.setDeadline(date);
            newExercise.setLevelType((LevelType) this.cbLevelTypeCreate.getSelectionModel().getSelectedItem());
            newExercise.setDescription(this.tfDescription.getText().trim());

            if (!this.tfNumber.getText().matches(NUMBERS_REGEX)) {
                this.lblErrorCreateModify.setText("🛈 Only numbers are allowed on the number fields.");
            }
            if (!this.tfHours.getText().matches(NUMBERS_REGEX)) {
                this.lblErrorCreateModify.setText("🛈 Only numbers are allowed on the hours fields.");
            }
            try {
                exerciseInterface.createExercise(newExercise);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Created successfully", ButtonType.OK);
                alert.showAndWait();
                tvExercise.refresh();

                //Clean fields
                this.tfNumber.setText("");
                this.tfHours.setText("");
                this.tfDescription.setText("");
                dpDeadline.setValue(null);

            } catch (CreateErrorException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to create", ButtonType.OK).showAndWait();
            }
        } catch (FindErrorException ex) {
            showErrorAlert("Error create");
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

    /**
     * Action event handler for modify button. It validates exercise data, send
     * it to the business logic tier and updates exercise table view with new
     * exercise data.
     *
     * @param event The ActionEvent object for the event.
     * @throws UpdateErrorException Thrown when any error or exception occurs
     * for update.
     * @throws FindErrorException Thrown when any error or exception occurs.
     */
    /*
    · El botón Modify se habilitará si se selecciona una fila de la tabla. 
    Todos los campos de texto, “tfSearch”, “tfNumber”, “tfDescription”, “tfHours”, 
    los botones “btmFileSolution”, “btmFile” y las combobox “cbUnitCreate” y 
    “cbLevelTypeCreate”, serán informadas con los datos de la fila de la tabla seleccionada.
    · Se validará si algún campo de los que se ha mencionado anteriormente ha sido modificado.
    · Si los botones “btmFileSolution” y “btmFile” se modifican, se llamará al método sendFile de la interfaz sendFileInterface.
    · Se mostrará un mensaje de confirmación cuando el botón se pulse:
    · En caso afirmativo, se llamará al método edit de la interfaz ExerciseInterface. Si todo ha ido correctamente se actualizará la tabla. Se limpiarán los campos de texto.
    · En caso negativo, se cerrará el mensaje de confirmación.
    · Si se produce algún error se le mostrará al usuario un mensaje de error con dicho error y no se realizará ningún cambio.
     */
    @FXML
    private void handleModifyButtonAction(ActionEvent event) throws UpdateErrorException, FindErrorException {
        try {
            Exercise exerciseModify = tvExercise.getSelectionModel().getSelectedItem();
            String unitName = cbUnitCreate.getSelectionModel().getSelectedItem().toString();
            List<Unit> units = unitInterface.findAllUnits();
            for (int i = 0; i < units.size(); i++) {
                if (units.get(i).getName().equalsIgnoreCase(unitName)) {
                    exerciseModify.setUnit(units.get(i));
                    i = units.size();
                }
            }

            exerciseModify.setNumber(tfNumber.getText().trim());
            exerciseModify.setHours(tfHours.getText().trim());
            LocalDate datePicker = dpDeadline.getValue();
            Date date = Date.from(datePicker.atStartOfDay(ZoneId.systemDefault()).toInstant());
            exerciseModify.setDeadline(date);
            exerciseModify.setLevelType((LevelType) cbLevelTypeCreate.getSelectionModel().getSelectedItem());
            exerciseModify.setDescription(tfDescription.getText().trim());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to modify this exercise?", ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            //If OK to modify
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //modify exercise from server side
                String itemId = exerciseModify.getId().toString();
                exerciseInterface.updateExercise(exerciseModify, itemId);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Successfully modified", ButtonType.OK);
                alert2.showAndWait();
                tvExercise.refresh();

                //Clean fields
                this.tfNumber.setText("");
                this.tfHours.setText("");
                this.tfDescription.setText("");
                dpDeadline.setValue(null);
            }

        } catch (UpdateErrorException | FindErrorException ex) {
            showErrorAlert("Error modify");
            LOGGER.log(Level.SEVERE,
                    ex.getMessage());
        }
    }

    /**
     * Action event handler for search button. It validates exercise data, send
     * it to the business logic tier and updates exercise table view with new
     * exercise data.
     *
     * @param event The ActionEvent object for the event.
     */
    /*
    · El botón Search se habilitará en los siguientes casos:
    · Si el campo Search “tfSearch” no está vacío y si las dos combobox, 
    “cbUnitSearch” y ”cbSearchType”, están seleccionadas.
    · Si el campo Search “tfSearch” no está vacío y en la combobox “cbSearchType” 
    está seleccionada la opción de búsqueda Unit name.
    · Si en la combobox “cbSearchType” está seleccionada la opción de búsqueda All.
    · Si se produce algún error se le mostrará al usuario un mensaje de error 
    con dicho error y no se realizará ningún cambio.

     */
    @FXML
    private void handleSearchButtonAction(ActionEvent event
    ) {
        LOGGER.info("Searching...");
        String unitValue;
        String searchValue = null;
        ObservableList<Exercise> exercise = null;

        try {
            unitValue = cbUnitSearch.getSelectionModel().getSelectedItem().toString();
            if (unitValue.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Select an unit", ButtonType.OK).showAndWait();
            } else {
                searchValue = cbSearchType.getSelectionModel().getSelectedItem().toString();
                if (searchValue.equalsIgnoreCase("All")) {
                    exerciseData = FXCollections.observableArrayList(exerciseInterface.findExercisesByUnitName(unitValue));
                    tvExercise.setItems((ObservableList) exerciseData);
                } else if (searchValue.equalsIgnoreCase("Level type")) {
                    exerciseData = FXCollections.observableArrayList(exerciseInterface.findExercisesByLevelAndUnitName(tfSearch.getText(), unitValue));
                    tvExercise.setItems((ObservableList) exerciseData);
                } else if (searchValue.equalsIgnoreCase("Number")) {
                    exerciseData = FXCollections.observableArrayList(exerciseInterface.findExercisesByNumberAndUnitName(tfSearch.getText(), unitValue));
                    tvExercise.setItems((ObservableList) exerciseData);
                }
            }
            if (exerciseData.isEmpty()) {
                new Alert(Alert.AlertType.INFORMATION, "There is no Exercise with that " + searchValue, ButtonType.OK).showAndWait();
            }
        } catch (ExerciseErrorException e) {
            new Alert(Alert.AlertType.INFORMATION, "There is no Unit with that " + searchValue, ButtonType.OK).showAndWait();
        }
    }

    /**
     * Action event handler for print button. It shows a JFrame containing a
     * report. This JFrame allows to print the report.
     *
     * @param event The ActionEvent object for the event.
     */
    /*
    · Si la tabla no está vacía, se imprimirá la información de la tabla en formato PDF.
     */
    @FXML
    private void handlePrintButtonAction(ActionEvent event
    ) {
        try {
            LOGGER.info("Beginning printing action...");
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/ExerciseReport.jrxml"));
            //Data for the report: a collection of UserBean passed as a JRDataSource 
            //implementation 
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Exercise>) this.tvExercise.getItems());
            //Map of parameter to be passed to the report
            Map<String, Object> parameters = new HashMap<>();
            //Fill report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            //Create and show the report window. The second parameter false value makes 
            //report window not to close app.
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
            // jasperViewer.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        } catch (Exception ex) {
            ex.printStackTrace();
            //If there is an error show message and
            //log it.
            LOGGER.log(Level.SEVERE,
                    "UI GestionUsuariosController: Error printing report: {0}",
                    ex.getMessage());
        }
    }

    /**
     * Action event handler for delete button. It asks user for confirmation on
     * delete, sends delete message to the business logic tier and updates
     * exercise table view.
     *
     * @param event The ActionEvent object for the event.
     * @throws DeleteErrorException FindErrorException Thrown when any error or
     * exception occurs for delete.
     */
    /*
    · El botón Delete se habilitará si se selecciona una fila de la tabla.
    · Se mostrará un mensaje de confirmación cuando el botón se pulse:
    · En caso afirmativo, se llamará al método remove de la interfaz ExerciseInterface. Si todo ha ido correctamente se actualizará la tabla.
    · En caso negativo, se cerrará el mensaje de confirmación.
    · Si se produce algún error se le mostrará al usuario un mensaje de error con dicho error y no se realizará ningún cambio.
     */
    @FXML
    private void handleDeleteButtonAction(javafx.event.ActionEvent event) throws DeleteErrorException {
        LOGGER.info("Deleting user...");
        try {
            //Get selected exercise data from table view model
            Exercise selectedExercise = ((Exercise) this.tvExercise.getSelectionModel().getSelectedItem());
            //Ask user for confirmation on delete
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected row?\n" + "This operation cannot be undone.", ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            //If OK to deletion
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //delete exercise from server side
                exerciseInterface.removeExercise(selectedExercise.getId().toString());
                //removes selected item from table
                this.tvExercise.getItems().remove(selectedExercise);
                this.tvExercise.refresh();
                //Clear selection and refresh table view
                this.tvExercise.getSelectionModel().clearSelection();
                this.tvExercise.refresh();
            }
        } catch (DeleteErrorException ex) {
            showErrorAlert("Error delete");
            LOGGER.log(Level.SEVERE,
                    ex.getMessage());
        }
    }

    /**
     * Action event handler for file send button. If the exercise selected from
     * the table does not have a file, you will be allowed to upload one. If the
     * exercise already has a file, an alert will appear.
     *
     * @param event The ActionEvent object for the event.
     */
    public void handleOnActionFileSend(Event event) {
        Exercise exerciseFile = tvExercise.getSelectionModel().getSelectedItem();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File...");
        if (exerciseFile.getFile() == null) {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                try {
                    Desktop.getDesktop().open(file);
                    // CALL SENDFILE INTERFACE
                    // SEND METHOD
                    fileInterface.sendFile(file, exerciseFile, "file");
                    tvExercise.refresh();
                } catch (IOException | UpdateErrorException ex) {
                    Logger.getLogger(Exercise.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (exerciseFile.getFile() != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "There is already a file selected for this exercise. It is not possible to upload another one.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Action event handler for file solution send button. If the exercise
     * selected from the table does not have a file solution, you will be
     * allowed to upload one. If the exercise already has a file, an alert will
     * appear.
     *
     * @param event The ActionEvent object for the event.
     */
    public void handleOnActionFileSolutionSend(Event event) {
        Exercise exerciseFileSolution = tvExercise.getSelectionModel().getSelectedItem();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File...");
        if (exerciseFileSolution.getFileSolution() == null) {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                try {
                    Desktop.getDesktop().open(file);
                    // CALL SENDFILE INTERFACE
                    // SEND METHOD
                    fileInterface.sendFile(file, exerciseFileSolution, "fileSolution");
                    tvExercise.refresh();
                } catch (IOException | UpdateErrorException ex) {
                    Logger.getLogger(Exercise.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (exerciseFileSolution.getFileSolution() != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "There is already a file solution selected for this exercise. It is not possible to upload another one.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Action event handler for file receive button. If the selected exercise
     * has a file it will download. If it is empty you will get an alert.
     *
     * @param event The ActionEvent object for the event.
     */
    public void handleOnActionFileReceive(Event event) {
        Exercise exerciseFile = tvExercise.getSelectionModel().getSelectedItem();
        // CALL SENDFILE INTERFACE
        // RECEIVE METHOD
        if (exerciseFile.getFile() != null) {
            try {
                File file = fileInterface.receiveFile(exerciseFile.getFile(), exerciseFile);
                Desktop.getDesktop().open(file);
            } catch (IOException | FindErrorException ex) {
                Logger.getLogger(Exercise.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "There are no files selected for this exercise yet.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Action event handler for file receive button. If the selected exercise
     * has a file it will download. If it is empty you will get an alert.
     *
     * @param event The ActionEvent object for the event.
     */
    public void handleOnActionFileSolutionReceive(Event event) {
        Exercise exerciseFileSolution = tvExercise.getSelectionModel().getSelectedItem();
        // CALL SENDFILE INTERFACE
        // RECEIVE METHOD
        if (exerciseFileSolution.getFileSolution() != null) {
            try {
                File file = fileInterface.receiveFile(exerciseFileSolution.getFileSolution(), exerciseFileSolution);
                Desktop.getDesktop().open(file);
            } catch (IOException | FindErrorException ex) {
                Logger.getLogger(Exercise.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "There are no files solution selected for this exercise yet.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Handles the exit event.
     *
     * @param event The event for the exit action.
     */
    public void handleOnActionExit(Event event) {
        try {
            LOGGER.info("EXIT pressed.");
            //Pedir confirmación al usuario para salir:
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to exit the application?",
                    ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                //Si el usuario confirma, cerrar la ventana.
                stage.close();
                LOGGER.info("Window closed.");
            } else {
                //Si no confirma, mantenerse en la ventana.
                event.consume();
                LOGGER.info("Aborted.");
            }
        } catch (Exception e) {
            String errorMsg = "Error exiting application:" + e.getMessage();
            new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK).showAndWait();

        }
    }

    /**
     * This method grab the name of the unit and put it in the combo box.
     *
     * @param unit The exercise unit.
     */
    public void setCurrentUnit(Unit unit) {
        this.unit = unit;
        String unitName = unit.toString();
        cbUnitSearch.getSelectionModel().select(unitName);
    }

    /**
     * Show error alert
     *
     * @param errorMsg Receive error string
     */
    protected void showErrorAlert(String errorMsg) {
        //Shows error dialog.
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Return the stage
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
