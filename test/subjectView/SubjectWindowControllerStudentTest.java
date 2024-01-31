/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subjectView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import main.SubjectMainStudent;
import models.Enrolled;
import models.Subject;
import models.User;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author irati
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SubjectWindowControllerStudentTest extends ApplicationTest {

    private TableView<Subject> tableView;
    private static User user;

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(SubjectMainStudent.class);
        user = new User();
        user.setId(2);
        user.setUser_type("Student");

    }

    @Test
    public void test1_matriculate() {
        tableView = lookup("#tbSubjects").query();
        int rowCount = tableView.getItems().size();
        Node row = lookup(".table-row-cell").nth(0).query();

        clickOn(row);

        Integer tablerow = tableView.getSelectionModel().getSelectedIndex();
        Node tableColumnMatriculated = lookup("#tbColMatriculated").nth(tablerow + 1).query();
        Subject subjectSelected = (Subject) tableView.getSelectionModel().getSelectedItem();
        Integer numberStudents = subjectSelected.getStudentsCount();

        Boolean subjecStatus = subjectSelected.getStatus();
        clickOn(tableColumnMatriculated);
        if (subjecStatus) {
            verifyThat("Are you sure you want to unenroll from this subject " + subjectSelected.getName() + " ?", isVisible());
            press(KeyCode.ENTER);
            Subject subjectUpdated = (Subject) tableView.getSelectionModel().getSelectedItem();
            boolean foundEnrollment = false;
            List<Subject> dataSubject = new ArrayList<>(tableView.getItems());
            for (Subject subject : dataSubject) {
                for (Enrolled enrolled : subject.getEnrollments()) {
                    if (enrolled.getId().getStudentId() == user.getId() && enrolled.getId().getSubjectId() == subjectSelected.getId() && !enrolled.getIsMatriculate()) {
                        foundEnrollment = true;
                    }
                }

            }
            assertTrue(foundEnrollment);
            assertTrue("La cadena no contiene el valor esperado", subjectUpdated.getStudentsCount().toString().equals(String.valueOf(numberStudents - 1)));

        } else {
            verifyThat("Do you want to enroll in the subject " + subjectSelected.getName() + " ?", isVisible());
            press(KeyCode.ENTER);
            List<Subject> dataSubject = new ArrayList<>(tableView.getItems());
            boolean foundEnrollment = false;
            Subject subjectUpdated = (Subject) tableView.getSelectionModel().getSelectedItem();
            for (Subject subject : dataSubject) {
                for (Enrolled enrolled : subject.getEnrollments()) {
                    if (enrolled.getId().getStudentId() == user.getId() && enrolled.getId().getSubjectId() == subjectSelected.getId() && enrolled.getIsMatriculate()) {
                        foundEnrollment = true;
                    }
                }

            }
            assertTrue(foundEnrollment);
            assertTrue("La cadena no contiene el valor esperado", subjectUpdated.getStudentsCount().toString().equals(String.valueOf(numberStudents + 1)));

        }

    }
}
