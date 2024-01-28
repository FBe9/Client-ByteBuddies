package view.subject;

import exceptions.CreateErrorException;
import exceptions.DeleteErrorException;
import exceptions.FindErrorException;
import exceptions.MaxLengthException;
import exceptions.SubjectNameAlreadyExistsException;
import exceptions.UpdateErrorException;
import exceptions.WrongDateFormatException;
import exceptions.WrongNameFormatException;
import exceptions.WrongNumberFormatException;
import factories.EnrolledFactory;
import factories.SubjectFactory;
import interfaces.EnrolledInterface;

import interfaces.SubjectManager;

import javafx.event.ActionEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Enrolled;
import models.EnrolledId;
import models.LanguageType;
import models.LevelType;
import models.Subject;
import models.Teacher;
import models.User;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

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
    private TableColumn tbColMatriculated;
    @FXML
    private TableColumn<Subject, String> tbColStudents;
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
    @FXML
    private TableColumn<Subject, String> tbColUnits;
    @FXML
    private TableColumn<Subject, String> tbColExams;
    @FXML
    private ImageView btnSearchSubjectima;

    private static final Logger LOGGER = Logger.getLogger("package view.subject");

    private Stage stage;
    private User user;

    private SubjectManager subjectManager;
    private EnrolledInterface enrolledInterface = EnrolledFactory.getModel();

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
        //Asignar user
        this.user = user;

        Scene scene = new Scene(root);
        stage.setScene(scene);

        // Configura el título de la ventana
        stage.setTitle("Subjects");

        // La ventana no es redimensionable
        stage.setResizable(false);
        //Establecer como botón por defecto
        btnSearchSubject.setDefaultButton(true);
        //El foco se centra en el campo tfSearchSubject.
        tfSearchSubject.requestFocus();
        //Poner el campo “dpDateSearchSubject” como no visible y el campo “tfSearchSubject” como visible. 
        dpDateSearchSubject.setVisible(false);
        tfSearchSubject.setVisible(true);

        //Cargar la comboBox “cbSearchSubject” con los siguientes  valores posibles: "Find Subjects by Name", "Find Subjects by Date Init", "Find Subjects by Date End",  "Find Subjects by Teacher Name",   "Find Subjects with at Least X number of units", “Find your subjects”. 
        ObservableList<String> cbItems = FXCollections.observableArrayList("name", "all subjects", "start date", "end date", "teacher name", "with at least _ number of units", "your subjects");
        //Si el usuario es un profesor añadir también a la combobox el siguiente valor:" with at least _ number of enrolled students"
        if (user.getUser_type().equals("Teacher")) {
            cbItems.add("with at least _ number of students");
            tbColMatriculated.setVisible(false);
            tbSubjects.setPrefWidth(994);
            btnPrintSubject.setLayoutX(1110);

        } else {
            //Si el usuario es un alumno ocultar los botones ‘btnNewSubject’ y ‘btnDeleteSubject’.
            btnCreateSubject.setVisible(false);
            btnDeleteSubject.setVisible(false);
        }

        cbSearchSubject.setItems(cbItems);
        // El combobox “cbSearchSubject” tendrá por defecto la opción “name”.
        cbSearchSubject.getSelectionModel().select("name");
        //Establecer el botón ‘btnSearchSubject’ como deshabilitado.
        btnSearchSubject.setDisable(true);

        //Si el usuario es un alumno ocultar los botones ‘btnNewSubject’ y ‘btnDeleteSubject’.
        // Designar la tabla como editable
        tbSubjects.setEditable(true);
        //Si el usuario es un estudiante solo podrá editar la columna tbColMatriculated
        if (user.getUser_type().equals("Student")) {
            tbColNameSub.setEditable(false);
            tbColHoursSub.setEditable(false);
            tbColInitDateSub.setEditable(false);
            tbColEndDateSub.setEditable(false);
            tbColLevelSub.setEditable(false);
            tbColLanguageSub.setEditable(false);
            tbColTeachersSub.setEditable(false);
        }

        //TABLA EDITABLE
        //Asignar valores a la tabla
        tbColNameSub.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbColHoursSub.setCellValueFactory(new PropertyValueFactory<>("hours"));
        tbColLevelSub.setCellValueFactory(new PropertyValueFactory<>("levelType"));
        tbColLanguageSub.setCellValueFactory(new PropertyValueFactory<>("languageType"));
        tbColInitDateSub.setCellValueFactory(new PropertyValueFactory<>("dateInit"));
        tbColEndDateSub.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
        tbColTeachersSub.setCellValueFactory(new PropertyValueFactory<>("teachers"));
        tbColStudents.setCellValueFactory(new PropertyValueFactory<>("studentsCount"));

        try {
            //Obtener toda la información de las asignaturas llamando al método findAllSubject de la interfaz SubjectManager.
            subjects = FXCollections.observableArrayList(
                    subjectManager.findAllSubjects());

        } catch (FindErrorException ex) {
            showErrorAlert(ex.getMessage());
        }
        if (!subjects.isEmpty()) {
            //Recoge el número de estudiantes
            for (Subject subject : subjects) {
                int matriculatedCount = 0;
                for (Enrolled enrollment : subject.getEnrollments()) {

                    if (enrollment.getIsMatriculate()) {
                        matriculatedCount++;
                    }
                }
                subject.setStudentsCount(matriculatedCount);
            }
        }

        //// Comprobar si hay asignaturas a medio editar (sin nombre) y borrarlas si es necesario
        List<Subject> subjectsToRemove = new ArrayList<>();
        for (Subject subject : subjects) {
            if (subject.getName() == null) {
                try {
                    subjectManager.deleteSubject(subject.getId().toString());

                    // Agregar la asignatura a la lista de asignaturas a eliminar
                    subjectsToRemove.add(subject);
                } catch (DeleteErrorException ex) {
                    Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (subjectsToRemove.size() > 0) {
            // Eliminar las asignaturas de la colección original fuera del bucle
            subjects.removeAll(subjectsToRemove);
        }

        //Agrega la lista a la tabla
        tbSubjects.setItems(subjects);
        //Callback para generar las factorias de celda
        final Callback<TableColumn<Subject, ObservableSet<Teacher>>, TableCell<Subject, ObservableSet<Teacher>>> listviewCell
                = (TableColumn<Subject, ObservableSet<Teacher>> param) -> new ListViewEditingCell();
        final Callback<TableColumn<Subject, Date>, TableCell<Subject, Date>> dateCell
                = (TableColumn<Subject, Date> param) -> new DateSubjectEditingCell();

        final Callback<TableColumn<Subject, String>, TableCell<Subject, String>> hyperlinkCellUnit
                = (TableColumn<Subject, String> param) -> new HyperLinkEditingCellUnit(user, stage);

        final Callback<TableColumn<Subject, String>, TableCell<Subject, String>> hyperlinkCellExam
                = (TableColumn<Subject, String> param) -> new HyperLinkEditingCellExam(user, stage);

        //Asignar las factorias de celda
        tbColTeachersSub.setCellFactory(listviewCell);
        tbColInitDateSub.setCellFactory(dateCell);
        tbColEndDateSub.setCellFactory(dateCell);
        tbColUnits.setCellFactory(hyperlinkCellUnit);
        tbColExams.setCellFactory(hyperlinkCellExam);

        // 1. Columna Name
        //Regex para las comprobaciones
        String regexLetters = "^[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ]+$";
        //Asignar la factoría de celdas
        tbColNameSub.setCellFactory(TextFieldTableCell.<Subject>forTableColumn());
        //Implementación on edit commit
        tbColNameSub.setOnEditCommit(
                (TableColumn.CellEditEvent<Subject, String> t) -> {
                    try {

                        //Coge el nuevo valor de la tabla
                        if (t.getNewValue().matches(regexLetters)) {
                            ((Subject) t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()))
                                    .setName(t.getNewValue());

                            // Obtener toda la información de las asignaturas llamando al método findAllSubject de la interfaz SubjectManager.
                            subjects = FXCollections.observableArrayList(subjectManager.findAllSubjects());

                            // Verificar si el nuevo nombre ya existe
                            for (Subject subjectCheck : subjects) {
                                if (subjectCheck.getName() != null) {
                                    if (subjectCheck.getName().equalsIgnoreCase(t.getNewValue())) {
                                        throw new SubjectNameAlreadyExistsException();
                                    }
                                }

                            }

                            // Tras la validación, llamar a la factoría SubjectFactory para obtener una implementación de la interfaz SubjectManager y llamar al método updateSubject
                            subjectManager.updateSubject(tbSubjects.getSelectionModel().getSelectedItem());
                        } else {
                            // En caso contrario, lanzar la excepción WrongNameFormatException
                            throw new WrongNameFormatException();
                        }

                        // Verificar la longitud del campo
                        if (t.getNewValue().length() > 100) {
                            throw new MaxLengthException();
                        }
                    } catch (SubjectNameAlreadyExistsException ex) {
                        showErrorAlert("Subject name already exists.");
                        loadData();

                    } catch (WrongNameFormatException ex) {
                        tbSubjects.refresh();
                        showErrorAlert("Invalid input " + t.getNewValue() + ". Please enter only letters.");
                    } catch (MaxLengthException ex) {
                        tbSubjects.refresh();
                        new Alert(Alert.AlertType.ERROR, "You've exceeded the maximum character limit for the field name.", ButtonType.OK).showAndWait();
                    } catch (FindErrorException | UpdateErrorException ex) {
                        showErrorAlert(ex.getMessage());
                    }
                });

        //2. Columna Teachers
        tbColTeachersSub.setOnEditCommit(
                (TableColumn.CellEditEvent<Subject, ObservableSet<Teacher>> t) -> {
                    Subject subject = t.getTableView().getItems().get(t.getTablePosition().getRow());

                    // Vaciar el array actual de profesores
                    //  subject.getTeachers().clear();
                    // Añadir los nuevos profesores seleccionados
                    subject.getTeachers().addAll(t.getNewValue());

                    try {
                        // Lógica para actualizar el sujeto en el backend
                        subjectManager.updateSubject(subject);
                    } catch (UpdateErrorException ex) {
                        showErrorAlert(ex.getMessage());
                        t.consume();
                    }
                }
        );

        //3. Columna LevelType
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
                        t.consume();
                        tbSubjects.refresh();

                    }
                });
        //4. Columna LanguageType
        //Establecer los valores para poder visualizarlos en el combobox
        ObservableList<LanguageType> languagesTypes = FXCollections.observableArrayList(LanguageType.SPANISH, LanguageType.BASQUE, LanguageType.ENGLISH);
        //Añadir la factoría de celdas para el combobox
        tbColLanguageSub.setCellFactory(ComboBoxTableCell.forTableColumn(languagesTypes));
        tbColLanguageSub.setOnEditCommit(
                (TableColumn.CellEditEvent<Subject, LanguageType> t) -> {

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
                        t.consume();
                        tbSubjects.refresh();
                    }

                });
        //5. Columna start date

        //Comprobar en el archivo de configuración cual es el periodo en el que se pueden añadir nuevas asignaturas
        String startDateConfig = ResourceBundle.getBundle("config.config").getString("STARTDATE");
        String endDateConfig = ResourceBundle.getBundle("config.config").getString("ENDDATE");

        LocalDate startDate = LocalDate.parse(startDateConfig);
        LocalDate endDate = LocalDate.parse(endDateConfig);
        tbColInitDateSub.setOnEditCommit(
                (TableColumn.CellEditEvent<Subject, Date> t) -> {
                    //Coger la fecha para hacer las comprobaciones
                    LocalDate dateInitAnalyze = t.getNewValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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
                            //Se cancelará la edición.
                            t.consume();
                            tbSubjects.refresh();
                        }
                    } else {
                        //Si las fechas no se encuentran dentro de ese periodo, se lanzará la excepción WrongDateException y se notificará al usuario mediante una alerta con el siguiente mensaje de error: 
                        //'Our application only stores information from year X to year Y. Please enter a date between these two years.'
                        try {

                            throw new WrongDateFormatException();
                        } catch (WrongDateFormatException ex) {
                            tbSubjects.refresh();
                            showErrorAlert("Our application only stores information from year " + startDate.getYear() + " to year " + endDate.getYear());
                        }
                    }

                }
        );
        //6. Columna end date 
        tbColEndDateSub.setOnEditCommit(
                (TableColumn.CellEditEvent<Subject, Date> t) -> {
                    Boolean checkDate = true;

                    //Coger la fecha para hacer las comprobaciones
                    Subject subject = (Subject) t.getTableView().getItems().get(t.getTablePosition().getRow());
                    Date dateInit = subject.getDateInit();
                    LocalDate dateEndAnalyze = t.getNewValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
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
                            tbSubjects.refresh();
                            showErrorAlert("Our application only stores information from year " + startDate.getYear() + " to year " + endDate.getYear());

                        }
                    }
                    //Se validará que la fecha de inicio sea anterior a la fecha fín, si no, 
                    //se lanzará la excepción WrongDateFormatException con el siguiente mensaje: 'The end date must be later than the start date of the subject.
                    if (dateEndAnalyze.isBefore(dateInitAnalyze)) {
                        checkDate = false;
                        try {
                            throw new WrongDateFormatException();
                        } catch (WrongDateFormatException ex) {
                            tbSubjects.refresh();
                            showErrorAlert("The end date must be later than the start date of the subject.");
                        }
                    }

                    if (checkDate) {
                        try {
                            ((Subject) t.getTableView().getItems()
                                    .get(t.getTablePosition().getRow()))
                                    .setDateEnd(t.getNewValue());
                            //Tras la validación y confirmación de que la información es correcta, se llamará a la factoria SubjectFactory para obtener una implematación de la interfaz SubjectManager 
                            //y llamar al método updateSubject, pasando como parámetro un objeto Subject con la información.
                            subjectManager.updateSubject(tbSubjects.getSelectionModel().getSelectedItem());
                        } catch (UpdateErrorException ex) {
                            //Si se produce algún error, se le mostrará al usuario una alerta con el error.  
                            showErrorAlert(ex.getMessage());
                            //Se cancelará la edición.
                            t.consume();
                            tbSubjects.refresh();
                        }
                    }

                }
        );
        //7. Columna hours
        //Regex para comprobar que solo introduce números
        String regexNumbers = "^[0-9]+$";

        //Establecer la factoría de celdas
        tbColHoursSub.setCellFactory(TextFieldTableCell.<Subject>forTableColumn());
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
                            showErrorAlert("Invalid input " + t.getNewValue() + ". Please enter only numbers.");
                            tbSubjects.refresh();
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
                            showErrorAlert("You've exceeded the maximum character limit for the field hours.");
                            tbSubjects.refresh();
                        }
                    }

                    if (checkHours) {
                        ((Subject) t.getTableView().getItems()
                                .get(t.getTablePosition().getRow()))
                                .setHours(t.getNewValue());

                        try {
                            subjectManager.updateSubject(tbSubjects.getSelectionModel().getSelectedItem());
                        } catch (UpdateErrorException ex) {
                            //Si se produce algún error, se le mostrará al usuario una alerta con el error.  
                            showErrorAlert(ex.getMessage());
                            //Se cancelará la edición.
                            t.consume();
                            //Se refresca la tabla
                            tbSubjects.refresh();

                        }
                    }

                }
        );
        // 11. Columna Enrolled
        tbColMatriculated.setCellFactory(
                CheckBoxTableCell.<Subject>forTableColumn(tbColMatriculated));
        //then, set the value cell factory:
        tbColMatriculated.setCellValueFactory(
                new PropertyValueFactory<>("status"));
        //Metodo para validar el checkbox. Se llamará a este método aquí y cuando se ponga el combobox a "find all subjects"
        checkBoxMatriculated();

        //Establecer los action y los listeners
        btnSearchSubject.setOnAction(
                this::handleSearchButtonAction);
        tfSearchSubject.textProperty()
                .addListener(this::textChanged);

        btnPrintSubject.setOnAction(this::handleImprimirAction);

        cbSearchSubject.valueProperty()
                .addListener(this::textChanged);
        dpDateSearchSubject.getEditor().textProperty().addListener(this::textChangedDtPicker);
        btnCreateSubject.setOnAction(
                this::handleCreateButtonAction);
        btnDeleteSubject.setOnAction(
                this::handleDeleteButtonAction);

        //Menus de contexto
        final ContextMenu contextMenu = new ContextMenu();
        //Menu de contexto para el create
        MenuItem createNewSubjectMenuItem = new MenuItem("Create new Subject");
        createNewSubjectMenuItem.setOnAction((ActionEvent e) -> {
        });
        //Menu de contexto para el delete
        MenuItem deleteSubjectMenuItem = new MenuItem("Delete a subject");
        deleteSubjectMenuItem.setOnAction((ActionEvent e) -> {
            deleteSubject();
        });
        //Menu de contexto para el print
        MenuItem printSubjectMenuItem = new MenuItem("Print a report");
        printSubjectMenuItem.setOnAction((ActionEvent e) -> {
            printAReport();
        }
        );
        //Si el usuario es un profesor añadir esos menus de contexto
        if (user.getUser_type()
                .equals("Teacher")) {
            contextMenu.getItems().add(createNewSubjectMenuItem);
            contextMenu.getItems().add(deleteSubjectMenuItem);
            contextMenu.getItems().add(printSubjectMenuItem);
            tbSubjects.setContextMenu(contextMenu);
        }
        btnDeleteSubject.setDisable(true);
        deleteSubjectMenuItem.setDisable(true);
        btnDeleteSubject.disableProperty().bind(Bindings.isEmpty(tbSubjects.getSelectionModel().getSelectedItems()));
        deleteSubjectMenuItem.disableProperty().bind(Bindings.isEmpty(tbSubjects.getSelectionModel().getSelectedItems()));
        //Mostrar la ventana.
        stage.setOnCloseRequest(this::handleOnActionExit);
        stage.show();

    }

    public void textChanged(ObservableValue observable, Object oldValue, Object newValue) {
        //Si el valor seleccionado es algunos de los siguientes: "name",  "teacher Name",   "with at Least _ number of units",  " with at least _ number of enrolled students", validar si “tfSearchSubject” está visible; si no lo está, establecerlo como visible. Luego, validar si “dpDateSearchSubject” no está visible; si no lo está, ponerlo en no visible.
        String selectedOption = (String) cbSearchSubject.getSelectionModel().getSelectedItem();
        //Quitamos el boton de matriculated para que solo se visualice cuando estan todas las asignaturas
        if (user.getUser_type().equals("Student")) {
            tbColMatriculated.setVisible(false);
            tbSubjects.setPrefWidth(994);
            btnPrintSubject.setLayoutX(1110);
        }

        if (selectedOption.equalsIgnoreCase("name") || selectedOption.equalsIgnoreCase("teacher name") || selectedOption.equalsIgnoreCase("with at least _ number of units") || selectedOption.equalsIgnoreCase("with at least _ number of students")) {
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
        if (newValue != null && !newValue.equals("")) {
            btnSearchSubject.setDisable(false);
        } else {
            btnSearchSubject.setDisable(true);
        }
    }

    public void handleSearchButtonAction(ActionEvent event) {
        String selectedOption = (String) cbSearchSubject.getSelectionModel().getSelectedItem();

        try {
            if (selectedOption.equalsIgnoreCase("name")) {

                try {
                    if (tfSearchSubject.getText() != null) {
                        subjects = FXCollections.observableArrayList(subjectManager.findSubjectsByName(tfSearchSubject.getText()));
                    }
                } catch (FindErrorException ex) {
                    showErrorAlert(ex.getMessage());
                }

            } else if (selectedOption.equalsIgnoreCase("teacher name")) {
                try {
                    if (tfSearchSubject.getText() != null) {
                        subjects = FXCollections.observableArrayList(subjectManager.findSubjectsByTeacher(tfSearchSubject.getText()));
                    }

                } catch (FindErrorException ex) {
                    showErrorAlert(ex.getMessage());
                }

            } else if (selectedOption.equalsIgnoreCase("start date")) {
                try {
                    if (dpDateSearchSubject.getValue() != null) {
                        subjects = FXCollections.observableArrayList(subjectManager.findSubjectByInitDate(dpDateSearchSubject.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
                    }
                } catch (FindErrorException ex) {
                    showErrorAlert(ex.getMessage());
                }

            } else if (selectedOption.equalsIgnoreCase("end date")) {
                try {
                    if (dpDateSearchSubject.getValue() != null) {
                        subjects = FXCollections.observableArrayList(subjectManager.findSubjectByEndDate(dpDateSearchSubject.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
                    }
                } catch (FindErrorException ex) {
                    showErrorAlert(ex.getMessage());
                }

            } else if (selectedOption.equalsIgnoreCase("with at least _ number of units")) {
                try {
                    if (tfSearchSubject.getText() != null) {
                        subjects = FXCollections.observableArrayList(subjectManager.findSubjectsWithXUnits(tfSearchSubject.getText()));
                    }
                } catch (FindErrorException ex) {
                    showErrorAlert(ex.getMessage());
                }
            } else if (selectedOption.equalsIgnoreCase("your subjects") && user.getUser_type().equals("Teacher")) {
                try {
                    subjects = FXCollections.observableArrayList(subjectManager.findSubjectsByTeacherId(user.getId().toString()));
                } catch (FindErrorException ex) {
                    showErrorAlert(ex.getMessage());
                }
            } else if (selectedOption.equalsIgnoreCase("your subjects") && user.getUser_type().equals("Student")) {
                try {
                    subjects = FXCollections.observableArrayList(subjectManager.findByEnrollments(user.getId().toString()));
                } catch (FindErrorException ex) {
                    showErrorAlert(ex.getMessage());
                }

            } else if (selectedOption.equalsIgnoreCase("all subjects")) {
                if (user.getUser_type().equals("Student")) {
                    tbColMatriculated.setVisible(true);
                    tbSubjects.setPrefWidth(1096);
                    btnPrintSubject.setLayoutX(1213);
                }
                try {
                    subjects = FXCollections.observableArrayList(subjectManager.findAllSubjects());
                } catch (FindErrorException ex) {
                    showErrorAlert(ex.getMessage());
                }

                checkBoxMatriculated();

            } else if (selectedOption.equalsIgnoreCase("with at least _ number of students")) {
                try {
                    if (tfSearchSubject.getText() != null) {
                        subjects = FXCollections.observableArrayList(subjectManager.findSubjectsWithEnrolledStudentsCount(tfSearchSubject.getText()));
                    }
                } catch (FindErrorException ex) {
                    showErrorAlert(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            showErrorAlert("Error trying to get the information");
            new Alert(Alert.AlertType.ERROR, "Error trying to get the information", ButtonType.OK).showAndWait();
        }

        if (!subjects.isEmpty()) {
            //Recoge el número de estudiantes
            for (Subject subject : subjects) {
                int matriculatedCount = 0;
                for (Enrolled enrollment : subject.getEnrollments()) {

                    if (enrollment.getIsMatriculate()) {
                        matriculatedCount++;
                    }
                }
                subject.setStudentsCount(matriculatedCount);
            }
        } else {
            // Display a message for no results
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("No Results");
            alert.setHeaderText(null);
            alert.setContentText("No results found for the search criteria.");

            alert.showAndWait();
        }
        tbSubjects.setItems((ObservableList) subjects);
        tfSearchSubject.clear();
        dpDateSearchSubject.setValue(null);

    }

    /**
     * Método para mostrar una alerta.
     *
     * @param errorMsg mensaje que recibe.
     */
    public void showErrorAlert(String errorMsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
        alert.showAndWait();

    }

    /**
     * Método para cargar los datos
     */
    private void loadData() {
        try {
            //Obtener toda la información de las asignaturas llamando al método findAllSubject de la interfaz SubjectManager.
            subjects = FXCollections.observableArrayList(
                    subjectManager.findAllSubjects());

        } catch (FindErrorException ex) {
            showErrorAlert(ex.getMessage());
        }
        if (!subjects.isEmpty()) {
            //Recoge el número de estudiantes
            for (Subject subject : subjects) {
                int matriculatedCount = 0;
                for (Enrolled enrollment : subject.getEnrollments()) {

                    if (enrollment.getIsMatriculate()) {
                        matriculatedCount++;
                    }
                }
                subject.setStudentsCount(matriculatedCount);
            }
        }if (!subjects.isEmpty()) {
            //Recoge el número de estudiantes
            for (Subject subject : subjects) {
                int matriculatedCount = 0;
                for (Enrolled enrollment : subject.getEnrollments()) {

                    if (enrollment.getIsMatriculate()) {
                        matriculatedCount++;
                    }
                }
                subject.setStudentsCount(matriculatedCount);
            }
        }
        if (!subjects.isEmpty()) {
            //Recoge el número de estudiantes
            for (Subject subject : subjects) {
                int matriculatedCount = 0;
                for (Enrolled enrollment : subject.getEnrollments()) {

                    if (enrollment.getIsMatriculate()) {
                        matriculatedCount++;
                    }
                }
                subject.setStudentsCount(matriculatedCount);
            }
        }
        tbSubjects.setItems(subjects);
        tbSubjects.refresh();
    }

    public void createSubject() {
        ObservableList<Subject> newSubjects = null;
        Boolean noEmptyName = false;
        /* for (Subject subject : newSubjects) {
            if (subject.getName() == null) {
                noEmptyName = true;
                showErrorAlert("It looks like you've started creating a subject, but you forgot to give it a name. Please complete that one before creating another.");
            }
        } */
        if (!noEmptyName) {
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
            loadData();
            
            //Poner siempre las asignaturas nuevas al final de la tabla
            for (int i = 0; i < subjects.size(); i++) {
                if (subjects.get(i).getName() == null) {
                    Subject nullNameSubject = subjects.remove(i);
                    subjects.add(nullNameSubject);
                }
            }
            // Mover todas las asignaturas con name igual a null al final de la lista
            List<Subject> nullNameSubjects = subjects.stream()
                    .filter(subject -> subject.getName() == null)
                    .collect(Collectors.toList());

            subjects.removeAll(nullNameSubjects);
            subjects.addAll(nullNameSubjects);

            tbSubjects.setItems(subjects);
            tbSubjects.refresh();
        }

    }

    public void deleteSubject() {
        Subject subjectDelete;
        subjectDelete = tbSubjects.getSelectionModel().getSelectedItem();
        if (subjectDelete != null) {
            //Se mostrará un mensaje de confirmación.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Do you want to delete the subject " + subjectDelete.getName() + " ?",
                    ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {
                try {
                    // Se verificará si existen matriculaciones para dicha asignatura; en caso afirmativo, se llamará a la factoría EnrolledFactory para obtener una implementación de la interfaz EnrolledManager 
                    //y se invocará al método deleteEnrolled, pasando como parámetro un objeto Enrolled. 
                    //se llamará a la factoría SubjectFactory para obtener una implementación de la interfaz SubjectManager y 
                    //se invocará al método deleteSubject, pasando como parámetro un objeto Subject.
                    if (subjectDelete.getEnrollments().size() > 0) {
                        for (Enrolled enrollment : subjectDelete.getEnrollments()) {
                            enrolledInterface.deleteEnrolled(enrollment.getId().getStudentId().toString(), enrollment.getId().getSubjectId().toString());
                        }
                    }
                    subjectManager.deleteSubject(subjectDelete.getId().toString());
                    loadData();

                } catch (DeleteErrorException ex) {
                    Logger.getLogger(SubjectController.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public void checkBoxMatriculated() {
        for (Subject subject : subjects) {
            int matriculatedCount = 0;
            for (Enrolled enrollment : subject.getEnrollments()) {
                if (user.getId() == enrollment.getId().getStudentId() && enrollment.getIsMatriculate()) {
                    subject.setStatus(true);
                }
                //Cogera cuantos alumnos estan matriculados para una asignatura para ponerlo en la columna students
                if (enrollment.getIsMatriculate()) {
                    matriculatedCount++;
                }
            }
            subject.setStudentsCount(matriculatedCount);
        }
        final boolean[] studentFound = {false};
        subjects.forEach(
                subject -> subject.statusProperty()
                        .addListener((observable, oldValue, newValue) -> {

                            //Se valida si el valor es falso o no para poner un mensaje en la alerta u otro.
                            if (oldValue == false) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                        "Do you want to enroll in the subject " + subject.getName() + " ?",
                                        ButtonType.YES, ButtonType.NO);
                                Optional<ButtonType> result = alert.showAndWait();

                                if (result.isPresent() && result.get() == ButtonType.YES) {
                                    //Si no hay entradas en enrollments

                                    if (subject.getEnrollments().size() > 0) {
                                        subject.getEnrollments().forEach(enrollment -> {
                                            //Se verifica que ya haya estado matriculado el alumno.
                                            if (user.getId() == enrollment.getId().getStudentId()) {
                                                try {
                                                    enrollment.setIsMatriculate(newValue);
                                                    subject.setStatus(newValue);
                                                    //Se llama al update de enrollment con el nuevo valor
                                                    enrolledInterface.updateEnrolled(enrollment);
                                                    //Se cambia el recuento de estudiante
                                                    subject.setStudentsCount(subject.getStudentsCount() + 1);
                                                    //Se refresca la tabla.
                                                    tbSubjects.refresh();

                                                    // Marca que se encontró al estudiante
                                                    studentFound[0] = true;

                                                } catch (UpdateErrorException ex) {
                                                    showErrorAlert(ex.getMessage());
                                                }
                                            }
                                        });

                                    }
                                    if (!studentFound[0] || subject.getEnrollments().isEmpty()) {
                                        Enrolled enrollmentNew = new Enrolled();
                                        EnrolledId enrolledId = new EnrolledId();
                                        enrolledId.setStudentId(user.getId());
                                        enrolledId.setSubjectId(subject.getId());
                                        enrollmentNew.setId(enrolledId);
                                        enrollmentNew.setIsMatriculate(newValue);
                                        subject.setStatus(newValue);
                                        try {
                                            //Crea la entrada
                                            enrolledInterface.createEnrolled(enrollmentNew);
                                            //Cambia la suma de estudiantes
                                            subject.setStudentsCount(subject.getStudentsCount() + 1);
                                            //Refresca la tabla
                                            tbSubjects.refresh();
                                        } catch (CreateErrorException ex) {
                                            showErrorAlert(ex.getMessage());
                                        }
                                    }
                                } else {
                                    loadData();
                                    checkBoxMatriculated();
                                }

                            } else {
                                //Si se quiere desmatricular
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                        "Are you sure you want to unenroll from this subject " + subject.getName() + " ?",
                                        ButtonType.YES, ButtonType.NO);
                                Optional<ButtonType> result = alert.showAndWait();

                                if (result.isPresent() && result.get() == ButtonType.YES) {
                                    subject.getEnrollments().forEach(enrollment -> {
                                        //Se encuentra al estudiante y se cambia el valor
                                        if (user.getId() == enrollment.getId().getStudentId()) {
                                            try {
                                                //Cambia el valor para el enrollment
                                                enrollment.setIsMatriculate(newValue);
                                                //Cambiar el valor
                                                subject.setStatus(newValue);
                                                //Cambiar el valor de enrolled
                                                enrolledInterface.updateEnrolled(enrollment);
                                                //Se cambia el número de estudiantes
                                                subject.setStudentsCount(subject.getStudentsCount() - 1);
                                                //Se refresca la tabla.
                                                tbSubjects.refresh();
                                            } catch (UpdateErrorException ex) {
                                                showErrorAlert(ex.getMessage());
                                            }
                                        }

                                    });
                                } else {
                                    loadData();
                                    checkBoxMatriculated();
                                }

                            }

                        }
                        )
        );
    }

    public void printAReport() {
        try {
            LOGGER.info("Beginning printing action...");
            JasperReport report
                    = JasperCompileManager.compileReport(getClass()
                            .getResourceAsStream("/reports/SubjectReport.jrxml"));
            //Data for the report: a collection of UserBean passed as a JRDataSource 
            //implementation 
            JRBeanCollectionDataSource dataItems
                    = new JRBeanCollectionDataSource((Collection<Subject>) this.tbSubjects.getItems());
            //Map of parameter to be passed to the report
            Map<String, Object> parameters = new HashMap<>();
            //Fill report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            //Create and show the report window. The second parameter false value makes 
            //report window not to close app.
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
            // jasperViewer.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        } catch (JRException ex) {
            //If there is an error show message and
            //log it.
            showErrorAlert("Error al imprimir:\n"
                    + ex.getMessage());
            LOGGER.log(Level.SEVERE,
                    "UI GestionUsuariosController: Error printing report: {0}",
                    ex.getMessage());
        }
    }

    private void handleImprimirAction(ActionEvent event) {
        printAReport();
    }

    public void handleCreateButtonAction(ActionEvent event) {
        createSubject();
    }

    public void handleDeleteButtonAction(ActionEvent event) {
        deleteSubject();
    }

    /**
     * Sets the stage for the window.
     *
     * @param stage The stage information for the window.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
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
                    "Are you sure you want to exit the application?",
                    ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            //If OK to exit
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //Borrar todas las asignaturas que se hayan creado pero que no se haya modificado el nombre
                Platform.exit();
            } else {
                event.consume();
            }
        } catch (Exception e) {
            String errorMsg = "Error exiting application:" + e.getMessage();
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    errorMsg,
                    ButtonType.OK);
            alert.showAndWait();
            LOGGER.log(Level.SEVERE, errorMsg);
        }
    }
}
