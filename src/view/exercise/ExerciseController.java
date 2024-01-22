/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.exercise;

import exceptions.CreateErrorException;
import exceptions.DeleteErrorException;
import exceptions.ExerciseErrorException;
import exceptions.FindErrorException;
import exceptions.UpdateErrorException;
import factories.ExerciseFactory;
import factories.UnitFactory;
import interfaces.ExerciseInterface;
import interfaces.UnitInterface;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;
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
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import models.Exercise;
import models.LevelType;
import models.Unit;
import models.User;

/**
 * FXML Controller class
 *
 * @author Leire
 */
public class ExerciseController {

    private static final Logger LOGGER = Logger.getLogger("Exercise view.");
    private Stage stage;
    private User user;
    private Exercise exercise;
    private ObservableList<Exercise> exerciseData;
    private ObservableList<Unit> unitData;
    private ExerciseInterface exerciseInterface;
    private UnitInterface unitInterface;
    private static final String NUMBERS_REGEX = "^\\d+$";
    private List<Exercise> byUnit = new ArrayList<>();

    @FXML
    private TableView tvExercise;
    @FXML
    private TableColumn tcUnit, tcNumber, tcDescription, tcLevelType, tcFile, tcFileSolution, tcDeadline, tcHours;
    @FXML
    private TextField tfSearch, tfNumber, tfDescription, tfHours;
    @FXML
    private ComboBox cbUnitSearch, cbSearchType, cbUnitCreate, cbLevelTypeCreate;
    @FXML
    private DatePicker dpDeadline;
    @FXML
    private Button btmSearch, btmDelete, btmModify, btmCreate, btmPrint, btmFile, btmFileSolution;
    private Label lblErrorCreateModify;

    /**
     * Method for initializing Exercise Stage.
     *
     * @param root The Parent object representing root node of view graph.
     * @param loggedUser
     * @throws exceptions.ExerciseErrorException
     * @throws exceptions.FindErrorException
     */
    public void initialize(Parent root, User loggedUser) throws ExerciseErrorException, FindErrorException {
        Scene scene = new Scene(root);
        LOGGER.info("Initializing the exercise window.");
        this.user = loggedUser;
        exerciseInterface = ExerciseFactory.getModel();
        unitInterface = UnitFactory.getModel();
        //El nombre de la ventana será “Exercise”.
        stage.setTitle("Exercise");

        //Se añadirá a la ventana el icono de una estrella.
        //-----
        //Ventana no redimensionable.
        stage.setResizable(false);

        //Se incluirá el MenuBar.fxml.
        //-----
        //Los botones “btmModify”, “btmCreate”,
        //“btmSearch” y “btmDelete” estarán deshabilitados, y el botón
        //”btmPrint” estará habilitado.
        this.btmModify.setDisable(true);
        this.btmCreate.setDisable(true);
        this.btmSearch.setDisable(true);
        this.btmDelete.setDisable(true);

        //Si el usuario que ha entrado a la aplicación es de tipo Student, 
        //los campos “tfSearch”, “tfNumber”, “tfDescription”,
        //“btmFile”, “btmFileSolution” y los combobox “cbUnitCreate” y 
        //“cbLevelTypeCreate” estarán deshabilitados.
        if (loggedUser.getUser_type().equalsIgnoreCase("Student")) {
            this.tfSearch.setDisable(true);
            this.tfNumber.setDisable(true);
            this.tfDescription.setDisable(true);
            this.tfHours.setDisable(true);
            this.btmFile.setDisable(true);
            this.btmFileSolution.setDisable(true);
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
                unitData = FXCollections.observableArrayList(unitInterface.findUnitsFromTeacherSubjects(loggedUser.getId()));
                if (unitData.isEmpty()) {
                    //Si el método no devuelve nada se rellena con el texto ”No Subjects found” y lo selecciona.
                    this.cbUnitCreate.getItems().add("No unit found");
                    cbUnitCreate.getSelectionModel().selectFirst();
                } else {
                    List<String> unitNames = new ArrayList<>();
                    for (int i = 0; i < unitData.size(); i++) {
                        String unitName = unitData.get(i).getName();
                        unitNames.add(unitName);
                    }
                    this.cbUnitCreate.getItems().addAll(unitNames);
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
        //tres tipos de nivel.
        this.cbLevelTypeCreate.getItems().addAll(LevelType.values());
        //this.cbLevelTypeCreate.setValue(LevelType.BEGGINER);

        //Se rellenará el combobox “cbUnitSearch” con una lista de unidades de 
        //las asignaturas en las cuales esté matriculado el usuario. 
        //El usuario puede ser de dos tipos:
        if (loggedUser.getUser_type().equalsIgnoreCase("Teacher")) {
            try {
                //Si el usuario es de tipo Teacher se obtendrá la información de las 
                //unidades llamando al método findUnitsFromTeacherSubjects de la 
                //interfaz UnitInterface para rellenar el combobox.
                unitData = FXCollections.observableArrayList(unitInterface.findUnitsFromTeacherSubjects(loggedUser.getId()));
                if (unitData.isEmpty()) {
                    //Si el método no devuelve nada se rellena con el texto ”No Subjects found” y lo selecciona.
                    this.cbUnitSearch.getItems().add("No unit found");
                    cbUnitSearch.getSelectionModel().selectFirst();
                } else {
                    List<String> unitNames = new ArrayList<>();
                    for (int i = 0; i < unitData.size(); i++) {
                        String unitName = unitData.get(i).getName();
                        unitNames.add(unitName);
                    }
                    this.cbUnitSearch.getItems().addAll(unitNames);
                }
            } catch (FindErrorException e) {
                LOGGER.log(Level.SEVERE, "Error searching for teacher unit");
            }
        } else {
            try {
                //Si el usuario es de tipo Student se obtendrá la información de las 
                //unidades llamando al método findUnitsFromStudentSubjects de la interfaz 
                //UnitInterface para rellenar el combobox.
                unitData = FXCollections.observableArrayList(unitInterface.findUnitsFromStudentSubjects(loggedUser.getId()));
                if (unitData.isEmpty()) {
                    //Si el método no devuelve nada se rellena con el texto ”No Subjects found” y lo selecciona.
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
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error searching for student unit");
            }
        }

        //Se rellenará el combobox “cbSearchType” con los tipos de búsqueda: 
        //Number, Level type y Date.
        //Si el usuario es de tipo Teacher también se añadirán los tipos de 
        //búsqueda: All y Unit name.
        if (loggedUser.getUser_type().equalsIgnoreCase("Teacher")) {
            this.cbSearchType.getItems().addAll("All", "Date", "Level type", "Number", "Unit name");
            //El combobox “cbSearchType” tendrá la opción de All por defecto.
            this.cbSearchType.setValue("All");
        } else {
            this.cbSearchType.getItems().addAll("Date", "Level type", "Number");
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
        //Las columnas “tcFile” y ”tcFileSolution” serán botones que descarguen 
        //el archivo. Se obtendrá la información de los archivos llamando al 
        //método receiveFile de la interfaz sendFileInterface.
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
        //Si el usuario es de tipo Teacher se obtendrá la información de los 
        //ejercicios  llamando al método getAllExercises de la interfaz 
        //ExerciseInterface.
        if (loggedUser.getUser_type().equalsIgnoreCase("Teacher")) {
            //Si el usuario es de tipo Teacher se obtendrá la información de los 
            //ejercicios  llamando al método getAllExercises de la interfaz 
            //ExerciseInterface.
            exerciseData = FXCollections.observableArrayList(exerciseInterface.getAllExercises_XML(new GenericType<List<Exercise>>() {
            }));
            this.tvExercise.setItems(exerciseData);
        } else {
            //Si el usuario es de tipo Student se obtendrá la información de los 
            //ejercicios  llamando al método getExercisesByUnitName de la 
            //interfaz ExerciseInterface.
            String unitName = this.cbUnitSearch.getSelectionModel().getSelectedItem().toString();
            exerciseData = FXCollections.observableArrayList(exerciseInterface.getExercisesByUnitName_XML(new GenericType<List<Exercise>>() {
            }, unitName));
            this.tvExercise.setItems(exerciseData);
        }

        //Add property change listeners for controls 
        this.tvExercise.getSelectionModel().selectedItemProperty()
                .addListener(this::handleExerciseTableSelectionChanged);
        this.tfSearch.textProperty().addListener(this::handleFieldsTextChange);
        this.tfNumber.textProperty().addListener(this::handleFieldsTextChange);
        this.tfHours.textProperty().addListener(this::handleFieldsTextChange);
        this.tfDescription.textProperty().addListener(this::handleFieldsTextChange);

        //
        //this.tfNumber.textProperty().addListener((event) -> this.textOnlyNumbers(KeyEvent.KEY_TYPED));
        //this.tfHours.textProperty().addListener((event) -> this.textOnlyNumbers(KeyEvent.KEY_TYPED));
        this.tfNumber.textProperty().addListener((event) -> this.textChangeCreate(KeyEvent.KEY_TYPED));
        this.tfHours.textProperty().addListener((event) -> this.textChangeCreate(KeyEvent.KEY_TYPED));
        this.tfDescription.textProperty().addListener((event) -> this.textChangeCreate(KeyEvent.KEY_TYPED));
        //
        //this.tfNumber.textProperty().addListener((event) -> this.textChangeModify(KeyEvent.KEY_TYPED));
        //this.tfHours.textProperty().addListener((event) -> this.textChangeModify(KeyEvent.KEY_TYPED));
        //this.tfDescription.textProperty().addListener((event) -> this.textChangeModify(KeyEvent.KEY_TYPED));

        stage.setScene(scene);
        //Mostrar la ventana. 
        stage.show();
    }

    private void handleExerciseTableSelectionChanged(ObservableValue observable,
            Object oldValue,
            Object newValue) {
        try {
            //If there is a row selected, move row data to corresponding fields in the
            //window and enable create, modify and delete buttons
            Exercise exercise = (Exercise) newValue;
            if (newValue != null) {

                /**
                 * If the table of Album is selected the fields are introduced
                 * in the designated textfields and enable create, modify and
                 * delete buttons
                 */
                int selectedRow = tvExercise.getSelectionModel().getSelectedIndex();

                String unitName = exercise.getUnit().toString();
                
                cbUnitCreate.getSelectionModel().select(unitName);
                tfNumber.setText(exercise.getNumber());
                //file
                //filesolution
                tfHours.setText(exercise.getHours());
                cbLevelTypeCreate.getSelectionModel().select(exercise.getLevelType());
                dpDeadline.setValue(exercise.getDeadline().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                tfDescription.setText(exercise.getDescription());
                this.btmModify.setDisable(false);
                this.btmDelete.setDisable(false);

            } else {
                //If there is not a row selected, clean window fields 
                //and disable create, modify and delete buttons
                tfNumber.setText("");
                //file
                //filesolution
                tfHours.setText("");
                dpDeadline.setValue(null);
                tfDescription.setText("");
                this.btmModify.setDisable(true);
                this.btmDelete.setDisable(true);
            }
        } catch (Exception e) {
            
        }
    }

    /**
     *
     * @param KEY_TYPED
     */
    private void textChangeCreate(int KEY_TYPED) {
        if (!this.cbUnitCreate.getSelectionModel().isEmpty() && !this.tfNumber.getText().trim().isEmpty() && !this.tfHours.getText().trim().isEmpty() && !this.cbLevelTypeCreate.getSelectionModel().isEmpty() && !this.tfDescription.getText().trim().isEmpty()) {
            this.btmCreate.setDisable(false);
        }
        if (this.cbUnitCreate.getSelectionModel().isEmpty() || this.tfNumber.getText().trim().isEmpty() || this.tfHours.getText().trim().isEmpty() || this.cbLevelTypeCreate.getSelectionModel().isEmpty() || this.tfDescription.getText().trim().isEmpty()) {
            this.btmCreate.setDisable(true);
        }
    }

    /**
     *
     * @param KEY_TYPED
     */
    /*private void textChangeModify(int KEY_TYPED) {
        if (!exercise.getNumber().equals(this.tfNumber.toString()) || !exercise.getHours().equals(this.tfHours.toString()) || !exercise.getDescription().equals(this.tfDescription.toString())) {
            this.btmModify.setDisable(false);
        }
    }*/
    /**
     * Text change event handler for search, number, hours and description
     * fields.
     *
     * @param observable The value being observed.
     * @param oldValue The old value of the observable.
     * @param newValue The new value of the observable.
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
     * Handle Action event on Continue button
     *
     * @param event The action event object
     */
    /*private void textOnlyNumbers(int KEY_TYPED) {
        /**
         * En el campo Number y Hours solo se permitirán caracteres numéricos.
         * Si en el campo hay caracteres no númericos se le comunicará en una
         * label un mensaje de color rojo: “Only numbers are allowed.”.
     */
 /* if (!this.tfNumber.getText().matches(NUMBERS_REGEX)) {
            this.lblErrorCreateModify.setText("🛈 Only numbers are allowed on the number field.");
        }
        if (!this.tfHours.getText().matches(NUMBERS_REGEX)) {
            this.lblErrorCreateModify.setText("🛈 Only numbers are allowed on the hours field.");
        }
    }*/
    /**
     *
     * @param event
     * @throws CreateErrorException
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
            //file
            //filesolution
            newExercise.setHours(this.tfHours.getText().trim());
            LocalDate datePicker = dpDeadline.getValue();
            Date date = Date.from(datePicker.atStartOfDay(ZoneId.systemDefault()).toInstant());
            newExercise.setDeadline(date);
            newExercise.setLevelType((LevelType) this.cbLevelTypeCreate.getSelectionModel().getSelectedItem());
            newExercise.setDescription(this.tfDescription.getText().trim());
            try {
                exerciseInterface.create_XML(newExercise);
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
        } catch (Exception ex) {
            showErrorAlert("Error create");
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

    @FXML
    private void handleModifyButtonAction(ActionEvent event) {
        try {
            String unitName = this.cbUnitCreate.getSelectionModel().getSelectedItem().toString();
            List<Unit> units = unitInterface.findAllUnits();
            Unit unit = new Unit();
            for (int i = 0; i < units.size(); i++) {
                if (units.get(i).getName().equalsIgnoreCase(unitName)) {
                    unit = units.get(i);
                }
            }
            exercise.setUnit(unit);
            exercise.setNumber(this.tfNumber.getText().trim());
            //file
            //filesolution
            exercise.setHours(this.tfHours.getText().trim());
            LocalDate datePicker = dpDeadline.getValue();
            Date date = Date.from(datePicker.atStartOfDay(ZoneId.systemDefault()).toInstant());
            exercise.setDeadline(date);
            exercise.setLevelType((LevelType) this.cbLevelTypeCreate.getSelectionModel().getSelectedItem());
            exercise.setDescription(this.tfDescription.getText().trim());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to modify this exercise?", ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            //If OK to modify
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //modify mental disease from server side
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Successfully modified", ButtonType.OK);
                alert2.showAndWait();

                //Clean fields
                this.tfNumber.setText("");
                this.tfHours.setText("");
                this.tfDescription.setText("");
                dpDeadline.setValue(null);
            }

        } catch (FindErrorException ex) {
            showErrorAlert("Error modify");
            LOGGER.log(Level.SEVERE,
                    ex.getMessage());
        }
    }

    @FXML
    private void handleSearchButtonAction(ActionEvent event) {
    }

    @FXML
    private void handlePrintButtonAction(ActionEvent event) {
    }

    /**
     *
     * @param event
     * @throws DeleteErrorException
     */
    @FXML
    private void handleDeleteButtonAction(javafx.event.ActionEvent event) throws DeleteErrorException {
        LOGGER.info("Deleting user...");
        try {
            //Get selected user data from table view model
            Exercise selectedExercise = ((Exercise) this.tvExercise.getSelectionModel().getSelectedItem());
            //Ask user for confirmation on delete
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected row?\n" + "This operation cannot be undone.", ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            //If OK to deletion
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //delete mental disease from server side
                exerciseInterface.remove(selectedExercise.getId().toString());
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
