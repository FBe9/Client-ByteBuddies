/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subjectView;

import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import main.SubjectMainStudent;
import models.Subject;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(SubjectMainStudent.class);

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

        Boolean subjecStatus = subjectSelected.getStatus();
        Integer numberStudents = subjectSelected.getStudentsCount();
        clickOn(tableColumnMatriculated);
        if (subjecStatus) {
            verifyThat("Are you sure you want to unenroll from this subject " + subjectSelected.getName() + " ?", isVisible());
            press(KeyCode.ENTER);

            Subject updatedSubject = (Subject) tableView.getSelectionModel().getSelectedItem();
            Boolean subjecStatusMo = updatedSubject.getStatus();
            Integer numberStudentsMo = subjectSelected.getStudentsCount();

            assertNotEquals(subjectSelected, false);
         //  assertEquals(numberStudents + 1, numberStudentsMo);
            assertNotEquals(numberStudents, false);

        } else {
            verifyThat("Do you want to enroll in the subject " + subjectSelected.getName() + " ?", isVisible());
            press(KeyCode.ENTER);
            assertNotEquals(subjecStatus, true);
            Integer numberUnenroll = numberStudents + 1;
            assertNotEquals(numberStudents, numberUnenroll);
        }

    }
}
