package view.exam;

import exceptions.CreateErrorException;
import exceptions.DeleteErrorException;
import exceptions.FindErrorException;
import exceptions.UpdateErrorException;
import factories.ExamFactory;
import factories.SubjectFactory;
import interfaces.ExamInterface;
import interfaces.SubjectManager;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Exam;
import models.Student;
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
 * Controller for the Exam window.
 *
 * @author Alex
 */
public class ExamWindowController {

    /**
     * Default constructor for the ExamWindowController class.
     */
    public ExamWindowController() {
    }

    // ComboBoxes
    /**
     * FXML element for search criteria ComboBox.
     */
    @FXML
    private ComboBox cbSearchCriteria;

    /**
     * FXML element for searching by subject ComboBox.
     */
    @FXML
    private ComboBox cbBySubject;

    // TextFields
    /**
     * FXML element for searching by exam name TextField.
     */
    @FXML
    private TextField tfSearchExam;

    // Buttons
    /**
     * FXML element for search action Button.
     */
    @FXML
    private Button btnSearchExam;

    /**
     * FXML element for exam creation action Button.
     */
    @FXML
    private Button btnCreateExam;

    /**
     * FXML element for exam deletion action Button.
     */
    @FXML
    private Button btnDeleteExam;

    /**
     * FXML element for printing exam action Button.
     */
    @FXML
    private Button btnPrintExam;

    /**
     * FXML element to cancel edit action Button.
     */
    @FXML
    private Button btnCancelExam;

    /**
     * FXML element for saving exam action Button.
     */
    @FXML
    private Button btnSaveExam;

    // TableViews
    /**
     * FXML element TableView of exam objects.
     */
    @FXML
    private TableView<Exam> tvExam;

    // Table Columns
    /**
     * FXML element for exam description TableColumn.
     */
    @FXML
    private TableColumn<Exam, String> tcDescription;

    /**
     * FXML element for exam subject TableColumn.
     */
    @FXML
    private TableColumn<Exam, Subject> tcSubject;

    /**
     * FXML element for exam duration TableColumn.
     */
    @FXML
    private TableColumn<Exam, String> tcDuration;

    /**
     * FXMl element for exam date TableColumn.
     */
    @FXML
    private TableColumn<Exam, Date> tcDate;

    /**
     * FXML element for exam file TableColumn.
     */
    @FXML
    private TableColumn<Exam, String> tcFile;

    // Stage
    /**
     * The stage of the Exam Window.
     */
    private Stage stage;

    // Application user
    /**
     * The current logged user.
     */
    private User currentUser;

    // Interfaces
    /**
     * Exam interface instance.
     */
    private ExamInterface examInterface;

    /**
     * Subject interface instance.
     */
    private SubjectManager subjectInterface;

    // Loggers
    /**
     * Logger for the ExamWindowController class.
     */
    private static final Logger LOGGER = Logger.getLogger("ExamWindowController");

    // Others
    private String allExams = "All exams";
    private String bySubject = "Exams by subject";
    private String searchExam = "Search an exam";
    private String noSubjects = "No subjects";
    private ObservableList<Subject> userSubjects = FXCollections.observableArrayList();
    private ObservableList<String> subjectNames = FXCollections.observableArrayList();
    private ObservableList<Exam> exams = FXCollections.observableArrayList();
    private Exam examEditing = new Exam();
    private Boolean flagNewRow = false;

    public void initStage(Parent root) {

        LOGGER.info("Initialising Exam view.");
        Scene scene = new Scene(root);
        stage.setScene(scene);

        // Llamar a las factorias necesarias
        examInterface = ExamFactory.getModel();
        subjectInterface = SubjectFactory.getModel();

        // El nombre de la ventana es “Exams”
        stage.setTitle("Exams");

        // Añadir a la ventana un icono de una estrella
        stage.getIcons().add(new Image("resources/Logo.jpg"));

        // Ventana no redimensionable
        stage.setResizable(false);

        // Listeners
        cbSearchCriteria.valueProperty().addListener(this::handlerComboBox);
        cbBySubject.valueProperty().addListener(this::handlerComboBox);
        tfSearchExam.textProperty().addListener(this::textPropertyChange);
        btnSearchExam.setOnAction(this::handlerButton);
        btnCreateExam.setOnAction(this::handlerButton);
        btnDeleteExam.setOnAction(this::handlerButton);
        btnPrintExam.setOnAction(this::handlerButton);
        btnCancelExam.setOnAction(this::handlerButton);
        btnSaveExam.setOnAction(this::handlerButton);
        stage.setOnCloseRequest(this::handleOnActionExit);

        // Ventana no modal        
        // Incluir el Menubar FXML
        // La ComboBox “cbBySubject”, el campo “tfSearchExam” y el botón “btnSearchExam” estarán deshabilitados
        cbBySubject.setDisable(true);
        tfSearchExam.setDisable(true);
        btnSearchExam.setDisable(true);

        // Los botones btnDeleteExam, btnCancelExam y btnSaveExam estarán deshabilitados para el usuario Teacher
        btnDeleteExam.setDisable(true);
        btnCancelExam.setDisable(true);
        btnSaveExam.setDisable(true);
        // Para Student estarán escondidos.
        if (currentUser.getUser_type().equals("Student")) {
            btnDeleteExam.setVisible(false);
            btnCancelExam.setVisible(false);
            btnSaveExam.setVisible(false);
            // Si el usuario es de tipo Student, también se esconderá el botón “btnCreateExam”. Si es Teacher será visible y estará habilitado
            btnCreateExam.setVisible(false);
        }
        if (currentUser.getUser_type().equals("Teacher")) {
            // Si es Teacher será visible y estará habilitado
            btnCreateExam.setVisible(true);
            btnCreateExam.setDisable(false);
        }

        // Se vacía el campo “tfSearchExam”
        tfSearchExam.setText("");

        // Se rellena la ComboBox “cbSearchCriteria” con tres valores: “All exams”, “Exams by subject” y “Search an exam”
        ObservableList<String> searchCriteria = FXCollections.observableArrayList(allExams, bySubject, searchExam);
        cbSearchCriteria.setItems(searchCriteria);
        // Por defecto se mostrará la opción “All exams”
        cbSearchCriteria.getSelectionModel().select(allExams);

        // Se rellena la ComboBox cbBySubject con los nombres de todas las asignaturas a las que pertenezca el usuario
        try {
            // Si el usuario es de tipo “Teacher” se usará el método “findSubjectsByTeacher” de la Interfaz “SubjectManager”
            if (currentUser.getUser_type().equals("Teacher")) {
                userSubjects = FXCollections.observableArrayList(subjectInterface.findSubjectsByTeacherId(currentUser.getId().toString()));
            }
            // Si el usuario es de tipo “Student” se usará el método “findByEnrollments” de la Interfaz “SubjectManager”
            if (currentUser.getUser_type().equals("Student")) {
                userSubjects = FXCollections.observableArrayList(subjectInterface.findByEnrollments(currentUser.getId().toString()));
            }
            userSubjects.forEach((s) -> {
                subjectNames.add(s.getName());
            });
            cbBySubject.setItems(subjectNames);
        } catch (FindErrorException ex) {
            LOGGER.log(Level.INFO, "No subjects found for user {0}", currentUser.getId());
            // Si el listado de asignaturas de cualquiera de los puntos anteriores está vacío, la ComboBox mostrará un texto indicando que el usuario
            // no tiene asignaturas
            cbBySubject.setItems(FXCollections.observableArrayList(noSubjects));
            cbBySubject.setValue(noSubjects);

            // Si el usuario es “Student”, aparecerá un mensaje en la tabla (Placeholder) indicando al usuario que debe matricularse para ver exámenes
            if (currentUser.getUser_type().equals("Student")) {
                tvExam.setPlaceholder(new Label("No exams here. Make sure you are enrolled in at least one subject."));
            }
            // Si es “Teacher” el mensaje indicará que debe crear exámenes.
            if (currentUser.getUser_type().equals("Teacher")) {
                tvExam.setPlaceholder(new Label("No exams here. Make sure to have at least one subject assigned to you."));
            }
            // También se deshabilitará el botón btnCreateExam
            btnCreateExam.setDisable(true);
        }

        // La tabla “tvExam” tendrá las siguientes columnas: “Description”, “Subject”, “Duration”, “Date” y “File"
        // Se carga la tabla “tvExam” con los valores de todos los exámenes que pertenezcan a todas las asignaturas del usuario
        if (userSubjects.size() > 0) {
            try {
                // Tanto si el usuario es de tipo Teacher o de tipo Student, se utilizará la colección obtenida en el apartado anterior para cargar los datos de la tabla
                for (Subject sub : userSubjects) {
                    exams.addAll(examInterface.findBySubject(sub.getId().toString()));
                }
            } catch (FindErrorException ex) {
                new Alert(Alert.AlertType.ERROR, "Couldn't find exams!", ButtonType.OK).showAndWait();
                LOGGER.log(Level.SEVERE, ex.getMessage());
            }
        }

        IntegerProperty disableRowIndex = new SimpleIntegerProperty();
        // El resto de columnas se rellenarán con los atributos de su mismo nombre.
        tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tcDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tcFile.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        // En el caso de la columna Subject se utilizará el nombre de la asignatura.
        tcSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        tcDescription.setOnEditStart((TableColumn.CellEditEvent<Exam, String> t) -> {
            cbSearchCriteria.setDisable(true);
            cbBySubject.setDisable(true);
            tfSearchExam.setDisable(true);
            btnSearchExam.setDisable(true);
            btnCreateExam.setDisable(true);
            btnPrintExam.setDisable(true);
            btnCancelExam.setDisable(false);
            btnSaveExam.setDisable(false);
            disableRowIndex.setValue(t.getTablePosition().getRow());

        });
        tcSubject.setOnEditStart((TableColumn.CellEditEvent<Exam, Subject> t) -> {
            cbSearchCriteria.setDisable(true);
            cbBySubject.setDisable(true);
            tfSearchExam.setDisable(true);
            btnSearchExam.setDisable(true);
            btnCreateExam.setDisable(true);
            btnPrintExam.setDisable(true);
            btnCancelExam.setDisable(false);
            btnSaveExam.setDisable(false);
        });
        tcDuration.setOnEditStart((TableColumn.CellEditEvent<Exam, String> t) -> {
            cbSearchCriteria.setDisable(true);
            cbBySubject.setDisable(true);
            tfSearchExam.setDisable(true);
            btnSearchExam.setDisable(true);
            btnCreateExam.setDisable(true);
            btnPrintExam.setDisable(true);
            btnCancelExam.setDisable(false);
            btnSaveExam.setDisable(false);
        });
        tcDate.setOnEditStart((TableColumn.CellEditEvent<Exam, Date> t) -> {
            cbSearchCriteria.setDisable(true);
            cbBySubject.setDisable(true);
            tfSearchExam.setDisable(true);
            btnSearchExam.setDisable(true);
            btnCreateExam.setDisable(true);
            btnPrintExam.setDisable(true);
            btnCancelExam.setDisable(false);
            btnSaveExam.setDisable(false);

        });
        tvExam.setRowFactory(tv -> {
            TableRow<Exam> row = new TableRow<>();
            tvExam.editingCellProperty().addListener((obs, oldCell, newCell) -> {
                if (newCell != null) {
                    row.setDisable(row.getIndex() != newCell.getRow());
                } else {
                    row.setDisable(false);
                }
            });
            return row;
        });
        tvExam.setItems(exams);

        // La tabla “tvExam” será editable
        tvExam.setEditable(true);
        if (currentUser.getUser_type().equals("Student")) {
            tcDescription.setEditable(false);
            tcSubject.setEditable(false);
            tcDuration.setEditable(false);
            tcDate.setEditable(false);
        }

        Callback<TableColumn<Exam, String>, TableCell<Exam, String>> cellFactory
                = (TableColumn<Exam, String> param) -> new CustomEditingCell();
        // La columna “Date” de la tabla tvExam se mostrará con un patrón formateado de acuerdo a la configuración del sistema operativo
        Callback<TableColumn<Exam, Date>, TableCell<Exam, Date>> dateCell
                = (TableColumn<Exam, Date> param) -> new DateExamEditingCell();
        // La columna File será un botón que maneje la subida y descarga del documento del enunciado de cada examen.
        Callback<TableColumn<Exam, String>, TableCell<Exam, String>> buttonCellFactory
                = (TableColumn<Exam, String> param) -> new ButtonEditingCell(currentUser, stage);

        tcDescription.setCellFactory(cellFactory);
        tcSubject.setCellFactory(ComboBoxTableCell.forTableColumn(userSubjects));
        tcDuration.setCellFactory(cellFactory);
        // La columna “Date” de la tabla “tvExam” se mostrará con un patrón formateado de acuerdo a la configuración del sistema
        tcDate.setCellFactory(dateCell);

        tcFile.setCellFactory(buttonCellFactory);

        tcDescription.setOnEditCommit(
                (TableColumn.CellEditEvent<Exam, String> t) -> {
                    if (!flagNewRow) {
                        examEditing = t.getRowValue();
                        //examEditing.setId(t.getRowValue().getId());
                    }
                    examEditing.setDescription(t.getNewValue());
                }
        );
        tcSubject.setOnEditCommit(
                (TableColumn.CellEditEvent<Exam, Subject> t) -> {
                    if (!flagNewRow) {
                        examEditing = t.getRowValue();
                        //examEditing.setId(t.getRowValue().getId());
                    }
                    examEditing.setSubject(t.getNewValue());
                });
        tcDuration.setOnEditCommit(
                (TableColumn.CellEditEvent<Exam, String> t) -> {
                    if (!flagNewRow) {
                        examEditing = t.getRowValue();
                        //examEditing.setId(t.getRowValue().getId());
                    }
                    examEditing.setDuration(t.getNewValue());
                }
        );
        tcDate.setOnEditCommit(
                (TableColumn.CellEditEvent<Exam, Date> t) -> {
                    if (!flagNewRow) {
                        examEditing = t.getRowValue();
                    }
                    examEditing.setDateInit(t.getNewValue());
                }
        );

        // El botón “btnPrintExam” [...] solo estará habilitado cuando la tabla “tvExam” tenga valores.
        if (tvExam.getItems().size() > 0) {
            btnPrintExam.setDisable(false);
        }

        btnDeleteExam.disableProperty().bind(Bindings.isEmpty(tvExam.getSelectionModel().getSelectedItems()));

        // Mostrar la ventana.
        stage.show();
        // stage.showAndWait();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(User user) {
        this.currentUser = user;
    }

    @FXML
    private void handlerComboBox(ObservableValue observable, Object oldValue, Object newValue) {
        if (observable == cbSearchCriteria.valueProperty()) {
            LOGGER.info("Observing cbSearchCriteria");
            if (cbSearchCriteria.getValue() == allExams) {
                // Si el valor seleccionado es “All exams” se carga la tabla “tvExam” con los valores de todos los exámenes que pertenezcan
                // a las asignaturas del usuario
                if (exams.isEmpty()) {
                    // Si no hay exámenes disponibles, se muestra en la tabla un mensaje indicando que el usuario no tiene exámenes
                    tvExam.setPlaceholder(new Label("No exams here."));
                } else {
                    tvExam.setItems(exams);
                }
                // Se vacía el campo “tfSearchExam”
                tfSearchExam.setText("");
                // Se deshabilitan la ComboBox “cbBySubject”, el campo “tfSearchExam” y el botón “btnSearchExam”
                cbBySubject.setDisable(true);
                cbBySubject.setValue("");
                tfSearchExam.setDisable(true);
                btnSearchExam.setDisable(true);
            } else if (cbSearchCriteria.getValue() == bySubject) {
                // Si el valor de la ComboBox “cbBySubject” indica que no hay asignaturas se mantendrá deshabilitada
                if (cbBySubject.getItems().size() > 0) {
                    if (!cbBySubject.getItems().get(0).equals(noSubjects)) {
                        // Si el valor seleccionado es “Exams by subject”, se habilita la ComboBox “cbBySubject”
                        cbBySubject.setDisable(false);
                    }
                }

                // Se vacía el campo “tfSearchExam”
                tfSearchExam.setDisable(true);
                // Se deshabilitan el campo “tfSearchExam” y el botón “btnSearchExam”
                tfSearchExam.setDisable(true);
                btnSearchExam.setDisable(true);
            } else if (cbSearchCriteria.getValue() == searchExam) {
                // Si el valor seleccionado es “Search an exam” se deshabilita la ComboBox “cbBySubject” y se habilitan el campo “tfSearchExam”
                // y el botón “btnSearchExam”
                cbBySubject.setDisable(true);
                cbBySubject.setValue("");
                tfSearchExam.setDisable(false);
            }
        }
        if (observable == cbBySubject.valueProperty()) {
            LOGGER.info("Observing cbBysubject");
            // Comprobar el valor que está seleccionado
            if (cbBySubject.getValue() != "") {
                // Se cargará la tabla con todos los exámenes del usuario que pertenezcan a la asignatura seleccionada de la forma en la que se ha explicado
                // anteriormente esta vez con una sola asignatura
                Subject subjectToSet = null;
                for (Subject sub : userSubjects) {
                    if (sub.getName().equals(cbBySubject.getValue())) {
                        subjectToSet = sub;
                    }
                }
                if (subjectToSet != null) {
                    // Si no hay, la tabla mostrará un mensaje (Placeholder) indicando que la asignatura seleccionada no tiene exámenes.
                    if (!exams.isEmpty()) {
                        ObservableList<Exam> currentExams = FXCollections.observableArrayList();
                        for (Exam e : exams) {
                            if (e.getSubject().getName().equals(subjectToSet.getName())) {
                                currentExams.add(e);
                            }
                        }
                        tvExam.setItems(currentExams);
                    } else {
                        tvExam.setPlaceholder(new Label("No exams here."));
                    }
                }
            }
        }
    }

    @FXML
    private void textPropertyChange(ObservableValue observable, String oldValue, String newValue) {
        if (observable == tfSearchExam.textProperty()) {
            LOGGER.info("Observing tfSearchExam");
            if (!tfSearchExam.getText().equals("") || !tfSearchExam.getText().equals(oldValue)) {
                // Si el campo “tfSearchExam” está informado y/o se modifica el texto, habilitar el botón “btnSearchExam”
                btnSearchExam.setDisable(false);
            }
            if (tfSearchExam.getText().equals("")) {
                // En caso de no estar informado, deshabilitar el botón “btnSearchExam”
                btnSearchExam.setDisable(true);
            }
        }
    }

    @FXML
    private void handlerButton(ActionEvent event) {
        if (event.getSource() == btnSearchExam) {
            LOGGER.info("btnSearchExam pressed.");
            // Validar que el texto introducido no supere los 300 caracteres
            if (tfSearchExam.getText().length() > 300) {
                // Si supera los 300 caracteres se deshabilitará el botón “btnSearchExam” y se mostrará un mensaje indicando que se ha superado el límite
                btnSearchExam.setDisable(true);
                new Alert(Alert.AlertType.WARNING, "Character limit (300) exceeded. ", ButtonType.OK).showAndWait();
            } else {
                // Una vez valide correctamente, se realiza la búsqueda usando la colección de asignaturas recopilada anteriormente
                ObservableList<Exam> searchedExams = FXCollections.observableArrayList();
                //ObservableList<Exam> currentExams = FXCollections.observableArrayList(setExams(userSubjects));
                if (exams.size() > 0) {
                    // Si la búsqueda arroja resultados se actualizará la tabla
                    for (Exam e : exams) {
                        if (e.getDescription().contains(tfSearchExam.getText())) {
                            searchedExams.add(e);
                        }
                    }
                    tvExam.setItems(searchedExams);
                } else {
                    // En caso contrario la tabla mostrará un mensaje (Placeholder) indicando que no ha habido resultados
                    tvExam.setPlaceholder(new Label("No exams found."));
                }
            }
        }
        if (event.getSource() == btnCreateExam) {
            LOGGER.info("btnCreateExam pressed.");
            // Se muestran los botones “btnCancelExam” y “btnSaveExam”, pero se mantienen deshabilitados.
            btnCancelExam.setVisible(true);
            btnSaveExam.setVisible(true);
            btnCancelExam.setDisable(true);
            btnSaveExam.setDisable(true);
            // Todo el resto de botones, ComboBoxes y camps que no sean los mencionados o parte de la tabla se deshabilitarán
            cbSearchCriteria.setDisable(true);
            cbBySubject.setDisable(true);
            tfSearchExam.setDisable(true);
            btnSearchExam.setDisable(true);
            btnCreateExam.setDisable(true);
            btnPrintExam.setDisable(true);
            // Se crea una nueva fila con valores por defecto, donde todos los campos están vacíos
            ObservableList<Exam> visibleExams = tvExam.getItems();
            visibleExams.add(new Exam("", null, "", "", null));
            tvExam.setItems(visibleExams);
            int newIndex = tvExam.getItems().size() - 1;
            // Se asigna el foco a la columna “Description” de la nueva fila.
            tvExam.requestFocus();
            tvExam.getSelectionModel().select(newIndex);
            tvExam.getFocusModel().focus(newIndex, tcDescription);
            tvExam.edit(newIndex, tcDescription);
            flagNewRow = true;
            LOGGER.info("New row created.");
        }
        if (event.getSource() == btnDeleteExam) {
            LOGGER.info("btnDeleteExam pressed.");
            // Se muestra un mensaje de confirmación.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "The exam you selected will be deleted. You won't be able to recover this information.\nAre you sure you want to proceed?",
                    ButtonType.YES, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {
                LOGGER.info("Response yes. Delete exam.");
                try {
                    int selectedExamIndex = tvExam.getSelectionModel().getSelectedIndex();
                    Exam selectedDeleteExam = tvExam.getItems().get(selectedExamIndex);
                    // Si elige “Yes” se llamará al método “deleteExam” de la Interfaz “ExamInterface”, pasando como parámetro el objeto Exam entero seleccionado en la tabla
                    examInterface.deleteExam(selectedDeleteExam);
                    // Si ha elegido “Yes” y no ha ocurrido ningún error, la información de la tabla se actualizará
                    exams.remove(selectedExamIndex);
                    tvExam.setItems(exams);
                    tvExam.refresh();
                    new Alert(Alert.AlertType.INFORMATION, "Exam deleted succesfully.").showAndWait();
                } catch (DeleteErrorException ex) {
                    LOGGER.log(Level.SEVERE, "Error deleting exam: {0}", ex.getMessage());
                    new Alert(Alert.AlertType.ERROR, "Error deleting exam.").showAndWait();
                }
            }
            // Si elige “No”, la acción se cancelará
            // En cualquiera de todos los casos, el botón “btnDeleteExam” se deshabilitará
            tvExam.getSelectionModel().clearSelection(tvExam.getSelectionModel().getSelectedIndex()); // Esta linea causa que el boton btnDeleteExam se deshabilite
        }
        if (event.getSource() == btnPrintExam) {
            LOGGER.info("btnPrintExam pressed.");
            // Genera un informe con la información disponible de la tabla Exams
            try {
                JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/ExamReport.jrxml"));
                //Data for the report: a collection of UserBean passed as a JRDataSource 
                //implementation 
                JRBeanCollectionDataSource dataItems
                        = new JRBeanCollectionDataSource((Collection<Exam>) this.tvExam.getItems());
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
                new Alert(Alert.AlertType.ERROR, "Error al imprimir:\n" + ex.getMessage()).showAndWait();
                LOGGER.log(Level.SEVERE, "UI GestionUsuariosController: Error printing report: {0}", ex.getMessage());
            }
        }
        if (event.getSource() == btnCancelExam) {
            LOGGER.info("btnCancelExam pressed.");
            // Se muestra un mensaje de confirmación recordando al usuario que perderá los datos editados no guardados
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "All unsaved changes will be lost.\nAre you sure you want to proceed?",
                    ButtonType.YES, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            // Si elige “Yes” se cancela la edición, se revierten los datos de la tabla a su estado anterior a la edición actual.
            if (result.isPresent() && result.get() == ButtonType.YES) {
                LOGGER.info("Response yes. Cancel edit.");
                tvExam.setItems(exams);
                if (flagNewRow) {
                    tvExam.getItems().remove(tvExam.getItems().size() - 1);
                }
                tvExam.refresh();
                examEditing = new Exam();
                // Se deshabilita el botón “btnDeleteExam” y se esconden los botones “btnCancelExam” y “btnSaveExam” y se vuelven a
                // habilitar el resto de campos, ComboBoxes y botones que se han deshabilitado anteriormente.
                cbSearchCriteria.setDisable(false);
                //cbBySubject.setDisable(true);
                //tfSearchExam.setDisable(true);
                //btnSearchExam.setDisable(true);
                btnCreateExam.setDisable(false);
                btnPrintExam.setDisable(false);
                btnCancelExam.setDisable(true);
                btnSaveExam.setDisable(true);
                tvExam.getSelectionModel().clearSelection(tvExam.getSelectionModel().getSelectedIndex());
                flagNewRow = false;
            }
            // Si elige “No” se cierra la ventana del mensaje
        }
        if (event.getSource() == btnSaveExam) {
            LOGGER.info("btnSaveExam pressed.");
            // Se validará que todas las celdas estén informadas
            //Exam saveExam = tvExam.getSelectionModel().getSelectedItem();
            if (examEditing.getDescription().isEmpty() || examEditing.getSubject() == null
                    || examEditing.getDescription().isEmpty() || examEditing.getDateInit() == null) {
                // Si hay alguna celda que no tenga contenido, se avisará al usuario a través de una alerta indicando que debe rellenar todas las celdas.
                new Alert(Alert.AlertType.ERROR, "You must fill all cells to save an exam.").showAndWait();
            } else {
                // Se muestra un mensaje de confirmación indicando que se va a guardar nueva información, indicar también el nombre del examen que se va a guardar
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                        "A new exam will be saved: " + examEditing.getDescription(),
                        ButtonType.OK, ButtonType.CANCEL);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try {
                        // Si elige “Ok”, se comprueba si el Examen a guardar ya existe o no llamando al método “findExamById” de la Interfaz “ExamInterface”
                        try {
                            if (examInterface.findExamById(examEditing.getId()).getId() != 0) {
                                examInterface.updateExam(examEditing);
                            } else {

                            }
                        } catch (NullPointerException | FindErrorException ex1) {
                            LOGGER.info("Exam not found, creating a new one.");
                            Exam ex = new Exam();
                            Subject sub = new Subject();
                            ex.setDescription(examEditing.getDescription());
                            ex.setDuration(examEditing.getDuration());
                            ex.setSubject(examEditing.getSubject());
                            ex.setDateInit(examEditing.getDateInit());
                            examInterface.createExam(ex);
                            if (flagNewRow) {
                                exams.add(examEditing);
                            }
                        }
                    } catch (CreateErrorException | UpdateErrorException ex) {
                        Logger.getLogger(ExamWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    // Si elige “Cancel” se cancelará la actualización o creación.
                }

                cbSearchCriteria.setDisable(false);
                //cbBySubject.setDisable(true);
                //tfSearchExam.setDisable(true);
                //btnSearchExam.setDisable(true);
                btnCreateExam.setDisable(false);
                btnPrintExam.setDisable(false);
                btnCancelExam.setDisable(true);
                btnSaveExam.setDisable(true);
                tvExam.getSelectionModel().clearSelection(tvExam.getSelectionModel().getSelectedIndex());
                flagNewRow = false;
            }

        }
    }

    private void handleOnActionExit(Event event) {
        LOGGER.info("Exit pressed.");
        // Se observará que no se esté editando la tabla o que no haya datos sin guardar.

    }

    public void setCurrentSubject(Subject subject) {
        //this.subject = subject;
        //Se guarda en modo de texto la subject obtenida.
        String subjectName = subject.toString();
        //Se selecciona en la combobox cbSubjects.
        cbBySubject.getSelectionModel().select(subjectName);
    }
}
