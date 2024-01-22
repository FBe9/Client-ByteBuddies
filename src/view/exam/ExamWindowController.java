package view.exam;

import exceptions.FindErrorException;
import factories.ExamFactory;
import factories.SubjectFactory;
import interfaces.ExamInterface;
import interfaces.SubjectManager;
import java.util.Date;
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
import javafx.scene.control.Label;
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
import models.Exam;
import models.Student;
import models.Subject;
import models.Teacher;
import models.User;

/**
 *
 * @author Alex
 */
public class ExamWindowController {

    public ExamWindowController() {
    }
    // ComboBoxes
    @FXML
    private ComboBox cbSearchCriteria;
    @FXML
    private ComboBox cbBySubject;
    // TextFields
    @FXML
    private TextField tfSearchExam;
    // Buttons
    @FXML
    private Button btnSearchExam;
    @FXML
    private Button btnCreateExam;
    @FXML
    private Button btnDeleteExam;
    @FXML
    private Button btnPrintExam;
    @FXML
    private Button btnCancelExam;
    @FXML
    private Button btnSaveExam;
    // TableViews
    @FXML
    private TableView<Exam> tvExam;
    // Table Columns
    @FXML
    private TableColumn<Exam, String> tcDescription;
    @FXML
    private TableColumn<Exam, Subject> tcSubject;
    @FXML
    private TableColumn<Exam, String> tcDuration;
    @FXML
    private TableColumn<Exam, Date> tcDate;
    @FXML
    private TableColumn<Exam, String> tcFile;
    /*@FXML
    private TableColumn tcDescriptionEx;
    @FXML
    private TableColumn tcSubjectEx;
    @FXML
    private TableColumn tcDurationEx;
    @FXML
    private TableColumn tcDateEX;
    @FXML
    private TableColumn tcFileEX;*/
    // Stage
    private Stage stage;
    // Application user
    private User currentUser;
    // Interfaces
    private ExamInterface examInterface;
    private SubjectManager subjectInterface;
    // Loggers
    private static final Logger LOGGER = Logger.getLogger("ExamWindowController");
    // Others
    private String allExams = "All exams";
    private String bySubject = "Exams by subject";
    private String searchExam = "Search an exam";
    private String noSubjects = "No subjects";
    private ObservableList<Subject> userSubjects = FXCollections.observableArrayList();
    private ObservableList<String> subjectNames = FXCollections.observableArrayList();
    private ObservableList<Exam> exams = FXCollections.observableArrayList();

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
        stage.getIcons().add(new Image("resources/AllBlueStar.png"));

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
        stage.setOnCloseRequest(this::handleOnActionExit);
        /*tcDescription.setOnEditCommit(this::handlerOnEditCommit);
        tcSubject.setOnEditCommit(this::handlerOnEditCommit);
        tcDuration.setOnEditCommit(this::handlerOnEditCommit);
        tcDate.setOnEditCommit(this::handlerOnEditCommit);*/

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
        if (currentUser instanceof Student) {
            btnDeleteExam.setVisible(false);
            btnCancelExam.setVisible(false);
            btnSaveExam.setVisible(false);
        }
        // Si el usuario es de tipo Student, también se esconderá el botón “btnCreateExam”. Si es Teacher será visible y estará habilitado
        if (currentUser instanceof Student) {
            btnCreateExam.setVisible(false);
        }
        if (currentUser instanceof Teacher) {
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

        // Se rellena la ComboBox “cbBySubject” con todas las asignaturas a las que pertenezca el usuario
        try {
            // Si el usuario es de tipo “Teacher” se usará el método “findSubjectsByTeacher” de la Interfaz “SubjectManager”
            // Del listado que devuelve se usará el nombre de la asignatura (objeto “Subject”) para rellenar la ComboBox
            if (currentUser instanceof Teacher) {
                userSubjects = FXCollections.observableArrayList(subjectInterface.findSubjectsByTeacher(currentUser.getName()));
            }
            // Si el usuario es de tipo “Student” se usará el método “findByEnrollments” de la Interfaz “SubjectManager”
            // Del listado que devuelve se usará el nombre de la asignatura (objecto “Subject”) para rellenar la ComboBox
            if (currentUser instanceof Student) {
                userSubjects = FXCollections.observableArrayList(subjectInterface.findByEnrollments(currentUser.getId().toString()));
            }
            for (Subject s : userSubjects) {
                subjectNames.add(s.getName());
            }
            cbBySubject.setItems(subjectNames);
        } catch (FindErrorException ex) {
            LOGGER.log(Level.INFO, "No subjects found for user {0}", currentUser.getId());
            // Si el listado de asignaturas de cualquiera de los puntos anteriores está vacío, la ComboBox mostrará un texto indicando que el usuario
            // no tiene asignaturas
            //if (userSubjects.size() == 0) {
            cbBySubject.setItems(FXCollections.observableArrayList(noSubjects));
            cbBySubject.setValue(noSubjects);
            // También se deshabilitará el botón de crear exámenes.
            btnCreateExam.setDisable(true);
            // Si el usuario es “Student”, aparecerá un mensaje en la tabla (Placeholder) indicando al usuario que debe matricularse para ver exámenes
            if (currentUser instanceof Student) {
                tvExam.setPlaceholder(new Label("No exams here. Make sure you are enrolled in at least one subject."));
            }
            // Si es “Teacher” el mensaje indicará que debe crear exámenes.
            if (currentUser instanceof Teacher) {
                tvExam.setPlaceholder(new Label("No exams here. Make sure to have at least one subject assigned to you."));
            }
        }

        // La tabla “tvExam” tendrá las siguientes columnas: “Number”, “Description”, “Subject”, “Duration”, “Date” y “File"
        // Se carga la tabla “tvExam” con los valores de todos los exámenes que pertenezcan a todas las asignaturas del usuario
        if (userSubjects.size() > 0) {
            try {
                for (Subject sub : userSubjects) {
                    exams.addAll(examInterface.findBySubject(sub.getId().toString()));
                }
            } catch (FindErrorException ex) {
                new Alert(Alert.AlertType.ERROR, "Couldn't find exams!", ButtonType.OK).showAndWait();
                LOGGER.log(Level.SEVERE, ex.getMessage());
            }
        }
        tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tcSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        tcDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tcFile.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        tvExam.setItems(exams);

        // La tabla “tvExam” será editable.
        tvExam.setEditable(true);
        if (currentUser instanceof Student) {
            tcDescription.setEditable(false);
            tcSubject.setEditable(false);
            tcDuration.setEditable(false);
            tcDate.setEditable(false);
        }

        Callback<TableColumn<Exam, Date>, TableCell<Exam, Date>> dateCell
                = (TableColumn<Exam, Date> param) -> new DateExamEditingCell();

        Callback<TableColumn<Exam, String>, TableCell<Exam, String>> cellFactory
                = (TableColumn<Exam, String> param) -> new ButtonEditingCell(currentUser);

        tcDescription.setCellFactory(TextFieldTableCell.forTableColumn());
        tcSubject.setCellFactory(ComboBoxTableCell.forTableColumn(userSubjects));
        tcDuration.setCellFactory(TextFieldTableCell.forTableColumn());
        // La columna “Date” de la tabla “tvExam” se mostrará con un patrón formateado de acuerdo a la configuración del sistema
        tcDate.setCellFactory(dateCell);
        tcFile.setCellFactory(cellFactory);

        // El botón “btnPrintExam” [...] solo estará habilitado cuando la tabla “tvExam” tenga valores.
        if (tvExam.getItems().size() > 0) {
            btnPrintExam.setDisable(false);
        }

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

                // Si no hay exámenes disponibles, se muestra en la tabla un mensaje indicando que el usuario no tiene exámenes
                if (exams.isEmpty()) {
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

                    // Si la tabla no tiene exámenes, la tabla mostrará un mensaje (Placeholder) indicando que la asignatura seleccionada no tiene exámenes.
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
            String tfExam = tfSearchExam.getText();
            if (tfExam.length() > 300) {
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
            btnDeleteExam.setDisable(true);
            btnPrintExam.setDisable(true);
            // Se crea una nueva fila con valores por defecto, donde todos los campos están vacíos
            //ObservableList<Exam> thisExams = tvExam.getItems();
            exams.add(new Exam("", null, "", "", null));
            tvExam.setItems(exams);
            int newIndex = tvExam.getItems().size() - 1;
            // Se asigna el foco a la columna “Description” de la nueva fila.
            tvExam.requestFocus();
            tvExam.getSelectionModel().select(newIndex);
            tvExam.getFocusModel().focus(newIndex, tcDescription);
        }
        if (event.getSource() == btnDeleteExam) {
            LOGGER.info("btnDeleteExam pressed.");
            // Se muestra un mensaje de confirmación.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "The exam you selected will be deleted. You won't be able to recover this information.\nAre you sure you want to proceed?",
                    ButtonType.YES, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {
                // Si elige “Yes” se llamará al método “deleteExam” de la Interfaz “ExamInterface”, pasando como parámetro el objeto Exam entero seleccionado en la tabla

                // Si ha elegido “Yes” y no ha ocurrido ningún error, la información de la tabla se actualizará
            } else {
                // Si elige “No”, la acción se cancelará

            }
            // En cualquiera de todos los casos, el botón “btnDeleteExam” se deshabilitará
            btnDeleteExam.setDisable(true);
        }
        if (event.getSource() == btnPrintExam) {
            LOGGER.info("btnPrintExam pressed.");
            // Genera un informe con la información disponible de la tabla Exams
        }
        if (event.getSource() == btnCancelExam) {
            LOGGER.info("btnCancelExam pressed.");
            // Se muestra un mensaje de confirmación recordando al usuario que perderá los datos editados no guardados

            // Si elige “Yes” se cancela la edición, se revierten los datos de la tabla a su estado anterior a la edición actual. 
            // Si elige “No” se cierra la ventana del mensaje
            // Se deshabilita el botón “btnDeleteExam” y se esconden los botones “btnCancelExam” y “btnSaveExam” y se vuelven a
            // habilitar el resto de campos, ComboBoxes y botones que se han deshabilitado anteriormente.
        }
        if (event.getSource() == btnSaveExam) {
            LOGGER.info("btnSaveExam pressed.");
            // Se validará que todas las celdas estén informadas

            // Si hay alguna celda que no tenga contenido, se avisará al usuario a través de una alerta indicando que debe rellenar todas las celdas.
            // Se muestra un mensaje de confirmación indicando que se va a guardar nueva información, indicar también el nombre del examen que se va a guardar
            // Si elige “Cancel” se cancelará la actualización o creación.
            // Si elige “Ok”, se comprueba si el Examen a guardar ya existe o no llamando al método “findExamById” de la Interfaz “ExamInterface”
        }
    }

    private void handlerOnEditCommit(Event event) {

    }

    private void handleOnActionExit(Event event) {
        LOGGER.info("Exit pressed.");
        // Se observará que no se esté editando la tabla o que no haya datos sin guardar.

    }
}


/*

PALLETE

LOGGER.info("");

tvExam.setPlaceholder(new Label("No exams here."));

Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage, ButtonType.OK).showAndWait();

tcDescription.setCellValueFactory(new PropertyValueFactory<Exam, String>(""));

 */
