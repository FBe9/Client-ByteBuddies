package view.subject;

import exceptions.FindErrorException;
import factories.SubjectFactory;
import factories.TeacherFactory;
import interfaces.SubjectManager;
import interfaces.TeacherInterface;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import models.Subject;
import models.Teacher;

/**
 * Cell Factory for the listView of Teachers
 *
 * @author irati
 */
public class ListViewEditingCell extends TableCell<Subject, ObservableSet<Teacher>> {

    private javafx.scene.control.ListView<Teacher> listViewTeacher;
    private ObservableList<Teacher> teachersSearch;
    private ObservableList<Subject> subjects;
    private TeacherInterface teacherInterface = TeacherFactory.getModel();
    private SubjectManager subjectManager = SubjectFactory.getModel();

    /**
     * Initiates the editing process for the cell. Creates a ListView with
     * teachers for selection. Pre-selects teachers already associated with the
     * current subject. Commits changes on pressing Enter.
     */
    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            listViewTeacher = new ListView<>();
            //Habilitar la selección multiple en el modelo.
            listViewTeacher.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            //Recuperar toda la lista de profesores y de asignaturas
            try {
                teachersSearch = FXCollections.observableArrayList(teacherInterface.findAll());
                subjects = FXCollections.observableArrayList(subjectManager.findAllSubjects());
            } catch (FindErrorException ex) {
                Logger.getLogger(ListViewEditingCell.class.getName()).log(Level.SEVERE, null, ex);
            }
            listViewTeacher.setItems(teachersSearch);

            //Recorrer las colecciones para pre seleccionar los profesores que ya estan guardados para una asignatura concreta
            Subject currentSubject = getTableView().getItems().get(getIndex());
            for (Subject subject : subjects) {
                for (Teacher teachersSelect : subject.getTeachers()) {
                    for (int i = 0; i < listViewTeacher.getItems().size(); i++) {
                        Teacher listView = listViewTeacher.getItems().get(i);
                        if ((listView.getId() == teachersSelect.getId()) && (currentSubject.getId() == subject.getId())) {
                            // Encontrar la posición y seleccionar el elemento en el ListView
                            listViewTeacher.getSelectionModel().select(i);
                        }
                    }
                }
            }
            //Añadir evento para que al pulsar enter se accione
            listViewTeacher.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    if (currentSubject.getName() != null) {
                        // Obtener la lista de los profesores seleccionados
                        ObservableList<Teacher> selectedTeachersList = listViewTeacher.getSelectionModel().getSelectedItems();

                        // Pasar la list a Set para poder hacer el commit
                        ObservableSet<Teacher> selectedTeachersSet = FXCollections.observableSet();
                        selectedTeachersSet.addAll(selectedTeachersList);

                        // Commit los profesores seleccionados
                        commitEdit(selectedTeachersSet);
                    } else {
                        // Limpiar selección y salir del modo edición
                        listViewTeacher.getSelectionModel().clearSelection();
                        cancelEdit();  // Método que deberías tener para salir del modo de edición

                        // Mostrar un mensaje de error
                        showErrorAlert("To assign a teacher to a subject, the subject must have a name.");

                    }

                }
            });
            setText(null);
            setGraphic(listViewTeacher);
            // Solicitar el foco para el ListView
            listViewTeacher.requestFocus();
        }
    }

    /**
     * Updates the content of the cell based on the provided item. Displays the
     * names of selected teachers or remains empty if no teachers are selected.
     *
     */
    @Override
    public void updateItem(ObservableSet<Teacher> item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                setText(null);
                setGraphic(listViewTeacher);
            } else if (item != null) {

                //Añadir el nombre de los profesores. Uno por línea
                StringBuilder teachersNames = new StringBuilder();
                item.forEach(teacher -> {
                    if (teachersNames.length() > 0) {
                        teachersNames.append("\n");
                    }
                    teachersNames.append(teacher.toString());
                });
                //Mirar que haya al menos un profesor para poner el nombre
                if (item.size() > 0) {
                    setText(teachersNames.toString());
                } else {
                    //Si no hay profesores para esa asignatura se pondra en vacio
                    setText("");
                }

                setGraphic(null);
                setGraphic(null);
            }
        }
    }

    /**
     * Cancels the editing process and clears the graphic content.
     */
    @Override
    public void cancelEdit() {
        setGraphic(null);
        super.cancelEdit();
    }

    public void showErrorAlert(String errorMsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
        alert.showAndWait();

    }
}
