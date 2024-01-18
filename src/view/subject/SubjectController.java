/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.subject;

import exceptions.CreateErrorException;
import exceptions.DeleteErrorException;
import exceptions.FindErrorException;
import exceptions.MaxLengthException;
import exceptions.UpdateErrorException;
import exceptions.WrongDateFormatException;
import exceptions.WrongNameFormatException;
import exceptions.WrongNumberFormatException;
import factories.SubjectFactory;

import interfaces.SubjectManager;

import javafx.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.ws.rs.core.GenericType;
import models.LanguageType;
import models.LevelType;
import models.Student;
import models.Subject;
import models.Teacher;
import models.User;

/**
 * FXML Controller class
 *
 * @author irati
 */
public class SubjectController {

    @FXML
    private TableView<Subject> tbSubjects;
    @FXML
    private TableColumn<Subject, String> tbColNameSub;
    @FXML
    private TableColumn<Subject, ObservableSet<Teacher>> tbColTeachersSub;
    @FXML
    private TableColumn<Subject, LevelType> tbColLevelSub;
    @FXML
    private TableColumn<Subject, LanguageType> tbColLanguageSub;
    @FXML
    private TableColumn<Subject, Date> tbColInitDateSub;
    @FXML
    private TableColumn<Subject, Date> tbColEndDateSub;
    @FXML
    private TableColumn<Subject, String> tbColHoursSub;
    @FXML
    private TableColumn tbColUnits;
    @FXML
    private TableColumn tbColExams;
    @FXML
    private TableColumn tbColStudents;
    @FXML
    private TableColumn tbColMatriculated;
    @FXML
    private TextField tfSearchSubject;
    @FXML
    private ComboBox cbSearchSubject;
    @FXML
    private Button btnSearchSubject;
    @FXML
    private Button btnCreateSubject;
    @FXML
    private Button btnDeleteSubject;
    @FXML
    private Button btnPrintSubject;
    @FXML
    private DatePicker dpDateSearchSubject;

    private Stage stage;
    private User user;

    private SubjectManager subjectManager;

    private ObservableList<Subject> subjects;
    private ObservableList<Teacher> teachers;
    private ObservableSet<Teacher> teachersPrueba;
    private ResourceBundle configFile;

    public SubjectController() {
        subjectManager = SubjectFactory.getModel();

    }

    /**
     * Initialises the controller class.
     *
     * @param root The parent window.
     */
    public void initStage(Parent root, User user) {
        this.user = user;
        Teacher teacher = new Teacher();
        teacher.setId(4);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        //El nombre de la ventana es “Subjects”.
        stage.setTitle("Subjects");
        //La ventana no es redimensionable.
        stage.setResizable(false);
        //Poner el campo “dpDateSearchSubject” como no visible y el campo “tfSearchSubject” como visible. 
        dpDateSearchSubject.setVisible(false);
        tfSearchSubject.setVisible(true);

        //Cargar la comboBox “cbSearchSubject” con los siguientes  valores posibles: "Find Subjects by Name", "Find Subjects by Date Init", "Find Subjects by Date End",  "Find Subjects by Teacher Name",   "Find Subjects with at Least X number of units", “Find your subjects”. 
        ObservableList<String> cbItems = FXCollections.observableArrayList("name", "all subjects", "start date", "end date", "teacher name", "with at leats _ number of units", "your subjects");
        //Si el usuario es un profesor añadir también a la combobox el siguiente valor:" with at least _ number of enrolled students"
        if (user instanceof Teacher) {
            cbItems.add("with at least _ number of students");
            tbColMatriculated.setVisible(false);

        } else {
            btnCreateSubject.setVisible(false);
            btnDeleteSubject.setVisible(false);
        }

        cbSearchSubject.setItems(cbItems);
        // El combobox “cbSearchSubject” tendrá por defecto la opción “name”.
        cbSearchSubject.getSelectionModel().select("name");

        tbColTeachersSub.setOnEditCommit((TableColumn.CellEditEvent<Subject, ObservableSet<Teacher>> t) -> {
            Subject subject = t.getTableView().getItems().get(t.getTablePosition().getRow());

            // Assuming you want to update the teachers property of the subject
            subject.setTeachers(t.getNewValue());

            try {
                // Assuming subjectManager.updateSubject expects a Subject parameter
                subjectManager.updateSubject(subject);
            } catch (UpdateErrorException ex) {
                showErrorAlert(ex.getMessage());
            }
        });

        //La columna de la fecha de inicio (tbColStartDate) y fecha de finalización ((tbColEndDate) de la tabla de subject se mostrará en un formato que se ajuste a la configuración local del ordenador del usuario.
        //Establecer el botón ‘btnSearchSubject’ como deshabilitado.
        btnSearchSubject.setDisable(true);

        //Si el usuario es un alumno ocultar los botones ‘btnNewSubject’ y ‘btnDeleteSubject’.
        // Si el usuario es un profesor, se designará la tabla 'subjects' como editable.
        tbSubjects.setEditable(true);
        if (user instanceof Student ) {
            tbColNameSub.setEditable(false);
            tbColHoursSub.setEditable(false);
            tbColInitDateSub.setEditable(false);
            tbColEndDateSub.setEditable(false);
            tbColLevelSub.setEditable(false);
            tbColLanguageSub.setEditable(false);
        }

        //Establecer como botón por defecto
        btnSearchSubject.setDefaultButton(true);

        //TABLA EDITABLE
        //Asignar valores a la tabla
        tbColNameSub.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbColHoursSub.setCellValueFactory(new PropertyValueFactory<>("hours"));
        tbColLevelSub.setCellValueFactory(new PropertyValueFactory<>("levelType"));
        tbColLanguageSub.setCellValueFactory(new PropertyValueFactory<>("languageType"));
        tbColInitDateSub.setCellValueFactory(new PropertyValueFactory<>("dateInit"));
        tbColEndDateSub.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
        tbColTeachersSub.setCellValueFactory(new PropertyValueFactory<>("teachers"));
        try {
            //Obtener toda la información de las asignaturas llamando al método findAllSubject de la interfaz SubjectManager.
            subjects = FXCollections.observableArrayList(
                    subjectManager.findAllSubjects());

        } catch (FindErrorException ex) {
            showErrorAlert(ex.getMessage());
        }
        tbSubjects.setItems((ObservableList) subjects);
        //Callback para generar las factorias de celda
        final Callback<TableColumn<Subject, ObservableSet<Teacher>>, TableCell<Subject, ObservableSet<Teacher>>> listviewCell
                = (TableColumn<Subject, ObservableSet<Teacher>> param) -> new ListViewEditingCell();
        final Callback<TableColumn<Subject, Date>, TableCell<Subject, Date>> dateCell
                = (TableColumn<Subject, Date> param) -> new DateSubjectEditingCell();
        //Asignar las factorias de celda
        tbColTeachersSub.setCellFactory(listviewCell);
        tbColInitDateSub.setCellFactory(dateCell);
        tbColEndDateSub.setCellFactory(dateCell);

        // 1. Columna Name
        //Regex para las comprobaciones
        String regexLetters = "^[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ]+$";
        //Asignar la factoría de celdas
        tbColNameSub.setCellFactory(TextFieldTableCell.<Subject>forTableColumn());
        //Implementación on edit commit
        tbColNameSub.setOnEditCommit(
                (TableColumn.CellEditEvent<Subject, String> t) -> {
                    Subject subject = tbSubjects.getSelectionModel().getSelectedItem();
                    String name = subject.getName();
                    //Si se presiona la tecla ENTER, se verificará que la información ingresada en la celda consista únicamente en letras. 
                    if (t.getNewValue().matches(regexLetters)) {
                        ((Subject) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setName(t.getNewValue());

                        try {
                            //Tras la validación y confirmación de que la información es correcta, se llamará a la factoría SubjectFactory para obtener una implementación de la interfaz SubjectManager y llamar al método updateSubject
                            //pasando como parámetro un objeto Subject con la información actualizada del nombre de la asignatura. 
                            subjectManager.updateSubject(tbSubjects.getSelectionModel().getSelectedItem());
                        } catch (UpdateErrorException ex) {
                            //Si se produce algún error, se le mostrará al usuario una alerta con el error y se cancelará la edición.  
                            showErrorAlert(ex.getMessage());
                            ((Subject) t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()))
                                    .setName(t.getOldValue());

                        }
                    } else {
                        //En caso contrario, se lanzará la excepción WrongNameFormatException y
                        //se notificará al usuario a través de una alerta con el siguiente mensaje de error: "Invalid input {tbColNameSub}.  Please, enter only letters.
                        try {
                            throw new WrongNameFormatException();
                        } catch (WrongNameFormatException ex) {
                            showErrorAlert("Invalid input " + t.getNewValue() + ". Please enter only letters.");
                            ((Subject) t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()))
                                    .setName(t.getOldValue());

                        }
                    }
                    //También se verificará que el campo no contenga más de 100 caracteres. 
                    if (t.getNewValue().length() > 100) {
                        try {
                            //En caso contrario, lanzar la excepción MaxlengthException y se notificará al usuario a través de una alerta con el 
                            //siguiente mensaje de error: 'You've exceeded the maximum character limit for the field name.'
                            throw new MaxLengthException();
                        } catch (MaxLengthException ex) {
                            new Alert(Alert.AlertType.ERROR, "You've exceeded the maximum character limit for the field name.", ButtonType.OK).showAndWait();
                            ((Subject) t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()))
                                    .setName(t.getOldValue());
                        }
                    }

                });
        //2. Columna LevelType
        //Establecer los valores para poder visualizarlos en la combobox
        ObservableList<LevelType> levelTypes = FXCollections.observableArrayList(LevelType.BEGGINER, LevelType.MEDIUM, LevelType.EXPERIENCED);
        //Añadir la factoría de celdas para el combobox
        tbColLevelSub.setCellFactory(ComboBoxTableCell.forTableColumn(levelTypes));
        tbColLevelSub.setOnEditCommit(
                (TableColumn.CellEditEvent<Subject, LevelType> t) -> {
                    ((Subject) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setLevelType(t.getNewValue());
                    //Si se produce un error volver al valor anterior
                    Subject subject = (Subject) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    LevelType originalValueLevel = subject.getLevelType();
                    try {
                        //Se llamará a la factoría SubjectFactory para obtener una implematación de la interfaz SubjectManager y llamar al método updateSubject, 
                        //pasando como parámetro un objeto Subject con la información actualizada. 
                        subjectManager.updateSubject(tbSubjects.getSelectionModel().getSelectedItem());
                    } catch (UpdateErrorException ex) {
                        //Si se produce algún error, se le mostrará al usuario una alerta con el error.
                        showErrorAlert(ex.getMessage());
                        //Se cancelará la edición.
                        subject.setLevelType(originalValueLevel);
                        t.consume();
                    }
                });
        //3. Columna LanguageType
        //Establecer los valores para poder visualizarlos en el combobox
        ObservableList<LanguageType> languagesTypes = FXCollections.observableArrayList(LanguageType.SPANISH, LanguageType.BASQUE, LanguageType.ENGLISH);
        //Añadir la factoría de celdas para el combobox
        tbColLanguageSub.setCellFactory(ComboBoxTableCell.forTableColumn(languagesTypes));
        tbColLanguageSub.setOnEditCommit(
                (TableColumn.CellEditEvent<Subject, LanguageType> t) -> {
                    //Si se produce un error volver al valor anterior
                    Subject subject = (Subject) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    LanguageType originalValue = subject.getLanguageType();

                    ((Subject) t.getTableView().getItems()
                            .get(t.getTablePosition().getRow()))
                            .setLanguageType(t.getNewValue());

                    try {
                        //Se llamará a la factoría SubjectFactory para obtener una implematación de la interfaz SubjectManager y 
                        //llamar al método updateSubject, pasando como parámetro un objeto Subject con la información actualizada. 
                        subjectManager.updateSubject(tbSubjects.getSelectionModel().getSelectedItem());
                    } catch (UpdateErrorException ex) {
                        //Si se produce algún error, se le mostrará al usuario una alerta con el error.  
                        showErrorAlert(ex.getMessage());
                        //Se cancelará la edición.
                        subject.setLanguageType(originalValue);
                        t.consume();
                    }

                });
        //4. Columna start date

        //Comprobar en el archivo de configuración cual es el periodo en el que se pueden añadir nuevas asignaturas
        String startDateConfig = ResourceBundle.getBundle("config.config").getString("STARTDATE");
        String endDateConfig = ResourceBundle.getBundle("config.config").getString("ENDDATE");

        LocalDate startDate = LocalDate.parse(startDateConfig);
        LocalDate endDate = LocalDate.parse(endDateConfig);
        tbColInitDateSub.setOnEditCommit(
                (TableColumn.CellEditEvent<Subject, Date> t) -> {
                    //Coger la fecha para hacer las comprobaciones
                    Subject subject = (Subject) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    Date dateInit = t.getNewValue();
                    LocalDate dateInitAnalyze = dateInit.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    //Se validará que ambas fechas estén comprendidas dentro de un período de tiempo. 
                    // El periodo de tiempo válido será definido en un archivo de configuración y podrá ser modificado. 
                    if (dateInitAnalyze.isBefore(endDate) && dateInitAnalyze.isAfter(startDate)) {
                        try {
                            ((Subject) t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()))
                                    .setDateInit(t.getNewValue());
                            //Tras la validación y confirmación de que la información es correcta, se llamará a la factoria SubjectFactory para obtener una implematación de la interfaz SubjectManager 
                            //y llamar al método updateSubject, pasando como parámetro un objeto Subject con la información.
                            subjectManager.updateSubject(tbSubjects.getSelectionModel().getSelectedItem());
                        } catch (UpdateErrorException ex) {
                            //Si se produce algún error, se le mostrará al usuario una alerta con el error.  
                            showErrorAlert(ex.getMessage());
                            refresh();
                            //Se cancelará la edición.
                            t.consume();
                        }
                    } else {
                        //Si las fechas no se encuentran dentro de ese periodo, se lanzará la excepción WrongDateException y se notificará al usuario mediante una alerta con el siguiente mensaje de error: 
                        //'Our application only stores information from year X to year Y. Please enter a date between these two years.'
                        try {
                            throw new WrongDateFormatException();
                        } catch (WrongDateFormatException ex) {
                            showErrorAlert("Our application only stores information from year " + startDate.getYear() + " to year " + endDate.getYear());
                            refresh();
                        }
                    }

                }
        );
        //5. Columna end date 
        tbColEndDateSub.setOnEditCommit(
                (TableColumn.CellEditEvent<Subject, Date> t) -> {
                    Boolean checkDate = true;

                    //Coger la fecha para hacer las comprobaciones
                    Subject subject = (Subject) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    Date dateEnd = subject.getDateEnd();
                    Date dateInit = subject.getDateInit();
                    LocalDate dateEndAnalyze = dateEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate dateInitAnalyze = dateInit.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    //Se validará que ambas fechas estén comprendidas dentro de un período de tiempo. 
                    // El periodo de tiempo válido será definido en un archivo de configuración y podrá ser modificado. 
                    if (dateEndAnalyze.isBefore(endDate) && dateEndAnalyze.isAfter(startDate)) {
                        checkDate = true;
                    } else {
                        //Si las fechas no se encuentran dentro de ese periodo, se lanzará la excepción WrongDateException y se notificará al usuario mediante una alerta con el siguiente mensaje de error: 
                        //'Our application only stores information from year X to year Y. Please enter a date between these two years.'
                        try {
                            checkDate = false;
                            throw new WrongDateFormatException();
                        } catch (WrongDateFormatException ex) {
                            showErrorAlert("Our application only stores information from year " + startDate.getYear() + " to year " + endDate.getYear());
                            ((Subject) t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()))
                                    .setDateInit(t.getOldValue());
                        }
                    }

                    if (dateEndAnalyze.isBefore(dateInitAnalyze)) {
                        checkDate = false;
                        try {
                            throw new WrongDateFormatException();
                        } catch (WrongDateFormatException ex) {
                            showErrorAlert("The end date must be later than the start date of the subject.");
                            ((Subject) t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()))
                                    .setDateInit(t.getOldValue());
                        }
                    }

                    if (checkDate) {
                        try {
                            ((Subject) t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()))
                                    .setDateInit(t.getNewValue());
                            //Tras la validación y confirmación de que la información es correcta, se llamará a la factoria SubjectFactory para obtener una implematación de la interfaz SubjectManager 
                            //y llamar al método updateSubject, pasando como parámetro un objeto Subject con la información.
                            subjectManager.updateSubject(tbSubjects.getSelectionModel().getSelectedItem());
                        } catch (UpdateErrorException ex) {
                            //Si se produce algún error, se le mostrará al usuario una alerta con el error.  
                            showErrorAlert(ex.getMessage());
                            ((Subject) t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()))
                                    .setDateInit(t.getOldValue());
                            //Se cancelará la edición.
                            t.consume();
                        }
                    }

                }
        );
        //6. Columna hours
        //Regex para comprobar que solo introduce números
        String regexNumbers = "^[0-9]+$";

        //Establecer la factoría de celdas
        tbColHoursSub.setCellFactory(TextFieldTableCell.<Subject>forTableColumn());
        tbColMatriculated.setEditable(true);
        tbColHoursSub.setOnEditCommit(
                (TableColumn.CellEditEvent<Subject, String> t) -> {
                    Boolean checkHours = true;
                    //Se verificará que la información ingresada en la celda consista únicamente de números enteros. 
                    if (!t.getNewValue().matches(regexNumbers)) {
                        //. En caso contrario, se lanzará la excepción WrongNumberFormatException y se notificará al usuario a través de una alerta con el siguiente mensaje de error: 
                        //“Invalid input {tbColHoursSub}. Please enter only numbers.” 
                        try {
                            checkHours = false;
                            throw new WrongNumberFormatException();
                        } catch (WrongNumberFormatException ex) {
                            new Alert(Alert.AlertType.ERROR, "Invalid input " + t.getNewValue() + ". Please enter only numbers.", ButtonType.OK).showAndWait();
                            refresh();
                        }
                    }
                    //Se verificará que el campo no contenga más de 4 caracteres. 
                    if (t.getNewValue().length() > 4) {
                        //En caso contrario, lanzar la excepción MaxlengthException y se notificará al usuario a través de una alerta con el siguiente mensaje
                        //de error: 'You've exceeded the maximum character limit for the hours field.
                        try {
                            checkHours = false;
                            throw new MaxLengthException();
                        } catch (MaxLengthException ex) {
                            new Alert(Alert.AlertType.ERROR, "You've exceeded the maximum character limit for the field hours.", ButtonType.OK).showAndWait();
                            refresh();
                        }
                    }

                    if (checkHours) {
                        ((Subject) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setHours(t.getNewValue());

                        Subject subject = (Subject) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        String hours = subject.getHours();

                        try {
                            subjectManager.updateSubject(tbSubjects.getSelectionModel().getSelectedItem());
                        } catch (UpdateErrorException ex) {
                            //Si se produce algún error, se le mostrará al usuario una alerta con el error.  
                            showErrorAlert(ex.getMessage());
                            refresh();
                            //Se cancelará la edición.
                            t.consume();

                        }
                    }

                }
        );
        // 11. Columna Enrolled

        tbColMatriculated.setCellFactory(CheckBoxTableCell.<Subject>forTableColumn(tbColMatriculated));
        /*
         subjects.forEach(
                    subject -> subject.s
                                .addListener((observable, oldValue, newValue) -> {
                        LOGGER.log(Level.INFO,
                                     "Status property changed.newvalue {0}",
                                      newValue.toString());
                        LOGGER.log(Level.INFO,             
                                     "User modified: {0}",
                                      user.getNombre());
                    })
            );
        /// */
        //Establecer los action y los listeners
        btnSearchSubject.setOnAction(
                this::handelSearchButtonAction);
        tfSearchSubject.textProperty()
                .addListener(this::textChanged);

        cbSearchSubject.valueProperty()
                .addListener(this::textChanged);
        dpDateSearchSubject.valueProperty()
                .addListener(this::textChangedDtPicker);
        btnCreateSubject.setOnAction(
                this::handelCreateButtonAction);
        btnDeleteSubject.setOnAction(
                this::handelDeleteButtonAction);

        //Mostrar la ventana.
        stage.show();

    }

    /**
     * Sets the stage for the window.
     *
     * @param stage The stage information for the window.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void textChanged(ObservableValue observable, Object oldValue, Object newValue) {
        //Si el valor seleccionado es algunos de los siguientes: "name",  "teacher Name",   "with at Least _ number of units",  " with at least _ number of enrolled students", validar si “tfSearchSubject” está visible; si no lo está, establecerlo como visible. Luego, validar si “dpDateSearchSubject” no está visible; si no lo está, ponerlo en no visible.
        String selectedOption = (String) cbSearchSubject.getSelectionModel().getSelectedItem();

        if (selectedOption.equalsIgnoreCase("name") || selectedOption.equalsIgnoreCase("teacher name") || selectedOption.equalsIgnoreCase("with at leats _ number of units") || selectedOption.equalsIgnoreCase("with at least _ number of students")) {
            if (!tfSearchSubject.isVisible()) {
                tfSearchSubject.setVisible(true);
                dpDateSearchSubject.setVisible(false);
            }
        } else if (selectedOption.equalsIgnoreCase("start date") || selectedOption.equalsIgnoreCase("end date")) {
            if (!dpDateSearchSubject.isVisible()) {
                dpDateSearchSubject.setVisible(true);
                tfSearchSubject.setVisible(false);
            }
        } else {
            tfSearchSubject.setVisible(false);
            dpDateSearchSubject.setVisible(false);
        }

        if (!tfSearchSubject.getText().trim().isEmpty()) {
            btnSearchSubject.setDisable(false);
        } else {
            btnSearchSubject.setDisable(true);
        }

        if (selectedOption.equalsIgnoreCase("your subjects") || selectedOption.equalsIgnoreCase("all subjects")) {
            if (btnSearchSubject.isDisable()) {
                btnSearchSubject.setDisable(false);
            }
        }
    }

    public void textChangedDtPicker(ObservableValue observable, Object oldValue, Object newValue) {
        if (dpDateSearchSubject.getValue() != null) {
            btnSearchSubject.setDisable(false);
        } else {
            btnSearchSubject.setDisable(true);
        }
    }

    public void handelSearchButtonAction(ActionEvent event) {
        String selectedOption = (String) cbSearchSubject.getSelectionModel().getSelectedItem();
        ObservableList<Subject> subjectsResult = null;
        try {
            if (selectedOption.equalsIgnoreCase("name")) {

                try {
                    subjectsResult = FXCollections.observableArrayList(subjectManager.findSubjectsByName(tfSearchSubject.getText()));
                } catch (FindErrorException ex) {
                    showErrorAlert(ex.getMessage());
                }

            } else if (selectedOption.equalsIgnoreCase("teacher name")) {
                try {
                    subjectsResult = FXCollections.observableArrayList(subjectManager.findSubjectsByTeacher(tfSearchSubject.getText()));
                } catch (FindErrorException ex) {
                    showErrorAlert(ex.getMessage());
                }

            } else if (selectedOption.equalsIgnoreCase("start date")) {
                try {
                    subjectsResult = FXCollections.observableArrayList(subjectManager.findSubjectByInitDate(dpDateSearchSubject.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
                } catch (FindErrorException ex) {
                    showErrorAlert(ex.getMessage());
                }

            } else if (selectedOption.equalsIgnoreCase("end date")) {
                try {
                    subjectsResult = FXCollections.observableArrayList(subjectManager.findSubjectByEndDate(dpDateSearchSubject.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
                } catch (FindErrorException ex) {
                    showErrorAlert(ex.getMessage());
                }

            } else if (selectedOption.equalsIgnoreCase("with at leats _ number of units")) {
                try {
                    subjectsResult = FXCollections.observableArrayList(subjectManager.findSubjectsWithXUnits(tfSearchSubject.getText()));
                } catch (FindErrorException ex) {
                    showErrorAlert(ex.getMessage());
                }
            } else if (selectedOption.equalsIgnoreCase("your subjects") && user instanceof Teacher) {
                try {
                    subjectsResult = FXCollections.observableArrayList(subjectManager.findSubjectsByTeacherId(user.getId().toString()));
                } catch (FindErrorException ex) {
                    showErrorAlert(ex.getMessage());
                }
            } else if (selectedOption.equalsIgnoreCase("your subjects") && user instanceof Student) {
                try {
                    subjectsResult = FXCollections.observableArrayList(subjectManager.findByEnrollments(user.getId().toString()));
                } catch (FindErrorException ex) {
                    showErrorAlert(ex.getMessage());
                }

            } else if (selectedOption.equalsIgnoreCase("all subjects")) {
                try {
                    subjectsResult = FXCollections.observableArrayList(subjectManager.findAllSubjects());
                } catch (FindErrorException ex) {
                    showErrorAlert(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            showErrorAlert("Error trying to get the information");
            new Alert(Alert.AlertType.ERROR, "Error trying to get the information", ButtonType.OK).showAndWait();
        }

        tbSubjects.setItems((ObservableList) subjectsResult);

    }

    public void showErrorAlert(String errorMsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
        alert.showAndWait();

    }

    public void handelCreateButtonAction(ActionEvent event) {
        ObservableList<Subject> newSubjects = null;
        try {
            newSubjects = FXCollections.observableArrayList(subjectManager.findAllSubjects());
        } catch (FindErrorException ex) {
            showErrorAlert(ex.getMessage());
        }
//Se creará una nueva fila con valores por defecto, donde todos los campos 'name' y 'hours' se inicializarán como nulos. 
//El campo 'Level Type' se fijará en 'Beginner', 'Language Type' se asignará como 'Spanish', y las fechas 'start date' y 'end date' tomarán la fecha del día de creación.
        Subject defaultSubject = new Subject();
        defaultSubject.setName(null);
        defaultSubject.setHours(null);
        defaultSubject.setLevelType(LevelType.BEGGINER);
        defaultSubject.setLanguageType(LanguageType.SPANISH);
        defaultSubject.setDateInit(new Date());
        defaultSubject.setDateEnd(new Date());

        try {
            //Después, se llamará a la factoría SubjectFactory para obtener una implementación de la interfaz SubjectManager 
            //y se invocará al método createSubject, pasando como parámetro un objeto Subject.
            subjectManager.createSubject(defaultSubject);
        } catch (CreateErrorException ex) {
            showErrorAlert(ex.getMessage());
        }
        //Si la operación se lleva a cabo sin errores, la fila recién creada se mostrará en la tabla.
        if (newSubjects != null) {
            newSubjects.add(defaultSubject);
            tbSubjects.setItems(newSubjects);
        }

    }

    public void handelDeleteButtonAction(ActionEvent event) {
        Subject subjectDelete = tbSubjects.getSelectionModel().getSelectedItem();
        //Se mostrará un mensaje de confirmación.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to exit the application?",
                ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Se verificará si existen matriculaciones para dicha asignatura; en caso afirmativo, se llamará a la factoría EnrolledFactory para obtener una implementación de la interfaz EnrolledManager 
                //y se invocará al método deleteEnrolled, pasando como parámetro un objeto Enrolled. 
                if (user instanceof Student) {

                }
                //se llamará a la factoría SubjectFactory para obtener una implementación de la interfaz SubjectManager y 
                //se invocará al método deleteSubject, pasando como parámetro un objeto Subject.
                subjectManager.deleteSubject(subjectDelete.getId().toString());
                refresh();

            } catch (DeleteErrorException ex) {
                Logger.getLogger(SubjectController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void refresh() {
        try {
            subjects = FXCollections.observableArrayList(subjectManager.findAllSubjects());
        } catch (FindErrorException ex) {
            showErrorAlert(ex.getMessage());
        }
        tbSubjects.setItems(subjects);
    }

}
