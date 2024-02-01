/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subjectView;

import exceptions.FindErrorException;
import factories.SubjectFactory;
import interfaces.SubjectManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.SubjectMainStudent;
import models.Enrolled;
import models.Subject;
import models.Teacher;
import models.User;
import static org.junit.Assert.assertTrue;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 * Class for testing subject window from student POV --> Just matriculation
 * enable.
 *
 * @author irati
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SubjectWindowControllerStudentTest extends ApplicationTest {

    /**
     * The TableView used to display subjects.
     */
    private TableView<Subject> tableView;

    /**
     * The user associated with the application.
     */
    private User user;

    /**
     * The SubjectManager responsible for managing subjects.
     */
    private SubjectManager subjectManager = SubjectFactory.getModel();

    /**
     * TextField used for performing searches.
     */
    private TextField search;

    /**
     * Button used to initiate searches or perform specific actions.
     */
    private Button buttonSearch;

    @Override
    public void start(Stage stage) throws Exception {
        new SubjectMainStudent().start(stage);
        user = new User();
        user.setId(2);
        user.setUser_type("Student");

    }

    /**
     * Method to enroll in a subject
     */
    @Test
    public void test1_enroll() {
        tableView = lookup("#tbSubjects").query();
        List<Subject> dataSubject = new ArrayList<>(tableView.getItems());
        Boolean notMatriculated = false;
        Integer position = null;
        Subject selectedSubject = null;
        //Busca en que asignaturas no esta matriculado para clickar en una de ellas.
        for (Subject subject : dataSubject) {
            if (subject.getStatus() == false) {
                notMatriculated = true;
                //Coge la posicíón de la asignatura modificada.
                position = dataSubject.indexOf(subject);
                selectedSubject = subject;
                break;

            }

        }
        //Si hay alguna en la que no esta matriculado.
        if (notMatriculated) {
            //Hace click en la fila con la posición recogida enteriormente.
            Node row = lookup(".table-row-cell").nth(position).query();
            clickOn(row);
            Node tableColumnMatriculated = lookup("#tbColMatriculated").nth(position + 1).query();
            Integer numberStudents = selectedSubject.getStudentsCount();
            clickOn(tableColumnMatriculated);
            //Verifica que se muestre el mensaje de confirmación
            verifyThat("Do you want to enroll in the subject " + selectedSubject.getName() + " ?", isVisible());
            press(KeyCode.ENTER);
            //Coge las asignaturas despues de hacer la modificación.
            ObservableList<Subject> allSubjects = null;
            try {
                allSubjects = FXCollections.observableArrayList(subjectManager.findAllSubjects());
            } catch (FindErrorException ex) {
                Logger.getLogger(SubjectWindowControllerStudentTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            boolean foundEnrollment = false;
            Subject subjectUpdated = (Subject) tableView.getSelectionModel().getSelectedItem();
            //Verifica que existe este enrolled con la información modificada.
            for (Subject subject : allSubjects) {
                for (Enrolled enrolled : subject.getEnrollments()) {
                    if (enrolled.getId().getStudentId() == user.getId() && enrolled.getId().getSubjectId() == selectedSubject.getId() && enrolled.getIsMatriculate()) {
                        foundEnrollment = true;
                        break;
                    }

                }

            }
            //Verifica que se haya encontrado
            assertTrue(foundEnrollment);
            //Verifica que se le haya sumado uno al valor de cuantos alumnos están matriculados.
            assertTrue("La cadena no contiene el valor esperado", subjectUpdated.getStudentsCount().toString().equals(String.valueOf(numberStudents + 1)));
        }

    }

    @Test
    public void test2_unenroll() {
        tableView = lookup("#tbSubjects").query();
        List<Subject> dataSubject = new ArrayList<>(tableView.getItems());
        Boolean matriculated = false;
        Integer position = null;
        Subject selectedSubject = null;
        //Busca en que asignaturas esta matriculado para clickar en una de ellas.
        for (Subject subject : dataSubject) {
            if (subject.getStatus() == true) {
                matriculated = true;
                //Coge la posición
                position = dataSubject.indexOf(subject);
                selectedSubject = subject;
                break;
            }
        }

        if (matriculated) {
            //Hace click en la fila con la posición recogida enteriormente.
            Node row = lookup(".table-row-cell").nth(position).query();
            clickOn(row);
            Node tableColumnMatriculated = lookup("#tbColMatriculated").nth(position + 1).query();
            Integer numberStudents = selectedSubject.getStudentsCount();
            clickOn(tableColumnMatriculated);
            //Verifica que se muestre el mensaje de confirmación
            verifyThat("Are you sure you want to unenroll from this subject " + selectedSubject.getName() + " ?", isVisible());

            press(KeyCode.ENTER);

            boolean foundEnrollment = false;
            Subject subjectUpdated = (Subject) tableView.getSelectionModel().getSelectedItem();
            ObservableList<Subject> allSubjects = null;
            try {
                allSubjects = FXCollections.observableArrayList(subjectManager.findAllSubjects());
            } catch (FindErrorException ex) {
                Logger.getLogger(SubjectWindowControllerStudentTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Verifica que existe este enrolled con la información modificada.
            for (Subject subject : allSubjects) {
                for (Enrolled enrolled : subject.getEnrollments()) {
                    if (enrolled.getId().getStudentId() == user.getId() && enrolled.getId().getSubjectId() == selectedSubject.getId() && !enrolled.getIsMatriculate()) {
                        foundEnrollment = true;

                    }
                }

            }
            //Verifica que se haya encontrado
            assertTrue(foundEnrollment);
            //Verifica que se le haya sumado uno al valor de cuantos alumnos están matriculados.
            assertTrue("La cadena no contiene el valor esperado", subjectUpdated.getStudentsCount().toString().equals(String.valueOf(numberStudents - 1)));

        }

    }

    @Test
    public void test3_searchSusSubjects() {
        tableView = lookup("#tbSubjects").query();
        ComboBox combo = lookup("#cbSearchSubject").query();
        buttonSearch = lookup("#btnSearchSubject").query();
        search = lookup("#tfSearchSubject").query();
        //Selecciona dentro del comboBox la búsqueda
        clickOn(combo);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        clickOn(buttonSearch);
        List<Subject> dataSubject = new ArrayList<>(tableView.getItems());
        Integer numberSubjects = 0;
        for (Subject subject : dataSubject) {
            boolean studentFound = false;
            for (Enrolled enrolled : subject.getEnrollments()) {
                if (enrolled.getId().getStudentId() == user.getId()) {
                    studentFound = true;
                    numberSubjects++;
                }
            }
            assertTrue(studentFound);
        }
        assertTrue("El número de asignaturas con el usuario no coincide con el tamaño total de la lista de asignaturas", tableView.getItems().size() == numberSubjects);

    }

}
