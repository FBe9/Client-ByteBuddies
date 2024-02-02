package examView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.ExamMain;
import models.Exam;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;

/**
 *
 * @author Alex
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExamWindowControllerTest extends ApplicationTest {

    private ComboBox cbSearchCriteria;
    private ComboBox cbBySubject;
    private TextField tfSearchExam;
    private Button btnSearchExam;
    private Button btnCreateExam;
    private Button btnDeleteExam;
    //private Button btnPrintExam;
    private Button btnCancelExam;
    private Button btnSaveExam;

    private TableView tvExam;

    @Override
    public void start(Stage stage) throws Exception {
        new ExamMain().start(stage);
    }

    @Ignore
    @Test
    public void test0_initStage() {
        cbSearchCriteria = lookup("#cbSearchCriteria").query();
        cbBySubject = lookup("#cbBySubject").query();
        tfSearchExam = lookup("#tfSearchExam").query();
        btnSearchExam = lookup("#btnSearchExam").query();
        btnCreateExam = lookup("#btnCreateExam").query();
        btnDeleteExam = lookup("#btnDeleteExam").query();
        btnCancelExam = lookup("#btnCancelExam").query();
        btnSaveExam = lookup("#btnSaveExam").query();

        tvExam = lookup("#tvExam").query();

        verifyThat(cbSearchCriteria, isEnabled());
        verifyThat(cbBySubject, isDisabled());
        verifyThat(tfSearchExam, isDisabled());
        verifyThat(btnSearchExam, isDisabled());
        verifyThat(btnCreateExam, isEnabled());
        verifyThat(btnDeleteExam, isDisabled());
        verifyThat(btnCancelExam, isDisabled());
        verifyThat(btnSaveExam, isDisabled());

        List<Exam> tableExams = new ArrayList<>(tvExam.getItems());
        Boolean isNull = false;
        for (Exam e : tableExams) {
            if (e.getDescription().isEmpty() || e.getSubject() == null
                    || e.getDuration().isEmpty() || e.getDateInit() == null) {
                isNull = true;
            }
        }
        
        assertTrue(tvExam.getItems().size() > 0);
        assertTrue(!isNull);
    }

    @Ignore
    @Test
    public void test1_createExam() {
        tvExam = lookup("#tvExam").query();
        Integer examCount = tvExam.getItems().size();
        clickOn("#btnCreateExam");
        assertEquals(examCount + 1, tvExam.getItems().size());

        Exam exam = (Exam) tvExam.getItems().get(tvExam.getItems().size() - 1);
        assertEquals(exam.getDescription(), "");
        assertEquals(exam.getDateInit(), null);
        assertEquals(exam.getDuration(), "");
        assertEquals(exam.getFilePath(), "");
        assertEquals(exam.getSubject(), null);

        Node newRow = lookup(".table-row-cell").nth(tvExam.getItems().size() - 1).query();
        //clickOn(newRow);
        Integer rowIndex = tvExam.getSelectionModel().getSelectedIndex();
        Node tcDescription = lookup("#tcDescription").nth(rowIndex + 1).query();
        Node tcDateInit = lookup("#tcDate").nth(rowIndex + 1).query();
        Node tcDuration = lookup("#tcDuration").nth(rowIndex + 1).query();
        Node tcSubject = lookup("#tcSubject").nth(rowIndex + 1).query();

        //Generates a randon number
        int randomNumber = new Random().nextInt(9000) + 1000;
        doubleClickOn(tcDescription);
        write("ABCD Exam" + randomNumber);
        doubleClickOn(tcDateInit);
        write("19/12/2025");
        doubleClickOn(tcDuration);
        write("1");
        doubleClickOn(tcSubject);
        push(KeyCode.DOWN);
        Exam e = (Exam) tvExam.getItems().get(rowIndex);
        String selecSubject = e.getSubject().getName();

        Exam customExam = (Exam) tvExam.getSelectionModel().getSelectedItem();
        assertEquals(customExam.getDescription(), "ABCD Exam" + randomNumber);
        LocalDate dateLocal = customExam.getDateInit().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        assertEquals(dateLocal.toString(), "2025-12-19");
        assertEquals(customExam.getDuration(), "1");
        assertEquals(customExam.getSubject().getName(), selecSubject);

        clickOn("#btnSaveExam");
        type(KeyCode.ENTER);
    }
    
    @Ignore
    @Test
    public void test1_createExamRightClick() {
        tvExam = lookup("#tvExam").query();
        Integer examCount = tvExam.getItems().size();
        //clickOn("#btnCreateExam");
        rightClickOn("#tvExam");
        assertEquals(examCount + 1, tvExam.getItems().size());

        Exam exam = (Exam) tvExam.getItems().get(tvExam.getItems().size() - 1);
        assertEquals(exam.getDescription(), "");
        assertEquals(exam.getDateInit(), null);
        assertEquals(exam.getDuration(), "");
        assertEquals(exam.getFilePath(), "");
        assertEquals(exam.getSubject(), null);

        Node newRow = lookup(".table-row-cell").nth(tvExam.getItems().size() - 1).query();
        //clickOn(newRow);
        Integer rowIndex = tvExam.getSelectionModel().getSelectedIndex();
        Node tcDescription = lookup("#tcDescription").nth(rowIndex + 1).query();
        Node tcDateInit = lookup("#tcDate").nth(rowIndex + 1).query();
        Node tcDuration = lookup("#tcDuration").nth(rowIndex + 1).query();
        Node tcSubject = lookup("#tcSubject").nth(rowIndex + 1).query();

        //Generates a randon number
        int randomNumber = new Random().nextInt(9000) + 1000;
        doubleClickOn(tcDescription);
        write("ABCD Exam" + randomNumber);
        doubleClickOn(tcDateInit);
        write("19/12/2025");
        doubleClickOn(tcDuration);
        write("1");
        doubleClickOn(tcSubject);
        push(KeyCode.DOWN);
        Exam e = (Exam) tvExam.getItems().get(rowIndex);
        String selecSubject = e.getSubject().getName();

        Exam customExam = (Exam) tvExam.getSelectionModel().getSelectedItem();
        assertEquals(customExam.getDescription(), "ABCD Exam" + randomNumber);
        LocalDate dateLocal = customExam.getDateInit().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        assertEquals(dateLocal.toString(), "2025-12-19");
        assertEquals(customExam.getDuration(), "1");
        assertEquals(customExam.getSubject().getName(), selecSubject);

        clickOn("#btnSaveExam");
        type(KeyCode.ENTER);
    }

    @Ignore
    @Test
    public void test2_ReadExam() {
        
    }

    @Ignore
    @Test
    public void test3_UpdateExam() {
        tvExam = lookup("#tvExam").query();
        Node updateRow = lookup(".table-row-cell").nth(0).query();
        clickOn(updateRow);
        Integer rowIndex = tvExam.getSelectionModel().getSelectedIndex();
        Node tcDescription = lookup("#tcDescription").nth(rowIndex + 1).query();
        Node tcDateInit = lookup("#tcDate").nth(rowIndex + 1).query();
        Node tcDuration = lookup("#tcDuration").nth(rowIndex + 1).query();
        Node tcSubject = lookup("#tcSubject").nth(rowIndex + 1).query();

        int randomNumber = new Random().nextInt(9000) + 1000;
        doubleClickOn(tcDescription);
        doubleClickOn(tcDescription);
        write("New Value Exam " + randomNumber);
        doubleClickOn(tcDateInit);
        write("22/11/2022");
        doubleClickOn(tcDuration);
        write("9");
        clickOn(tcSubject);
        clickOn(tcSubject);
        push(KeyCode.DOWN);
        Exam e = (Exam) tvExam.getItems().get(rowIndex);
        String selecSubject = e.getSubject().getName();

        Exam customExam = (Exam) tvExam.getSelectionModel().getSelectedItem();
        assertEquals(customExam.getDescription(), "New Value Exam " + randomNumber);
        LocalDate dateLocal = customExam.getDateInit().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        assertEquals(dateLocal.toString(), "2022-11-22");
        assertEquals(customExam.getDuration(), "9");
        assertEquals(customExam.getSubject().getName(), selecSubject);

        clickOn("#btnSaveExam");
        type(KeyCode.ENTER);
    }

    @Ignore
    @Test
    public void test4_DeleteExam() {
        tvExam = lookup("#tvExam").query();
        Integer tableSize = tvExam.getItems().size();
        Node deleteRow = lookup(".table-row-cell").nth(tvExam.getItems().size() - 1).query();
        clickOn(deleteRow);
        Exam selectedEx = (Exam) tvExam.getSelectionModel().getSelectedItem();
        String name = selectedEx.getDescription();
        clickOn("#btnDeleteExam");
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);

        List<Exam> tableExams = new ArrayList<>(tvExam.getItems());
        Boolean notFound = true;
        for (Exam e : tableExams) {
            if (e.getDescription().equalsIgnoreCase(name)) {
                notFound = false;
            }
        }
        assertEquals(tableSize - 1, tvExam.getItems().size());
        assertTrue(notFound);
    }

    @Ignore
    @Test
    public void test5_SearchBySubject() {
        cbBySubject = lookup("#cbBySubject").query();
        tvExam = lookup("#tvExam").query();
        clickOn("#cbSearchCriteria");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#cbBySubject");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        String selectedSub = (String) cbBySubject.getValue();
        List<Exam> searchedBySub = new ArrayList<>(tvExam.getItems());
        Boolean notFound = false;
        for(Exam e : searchedBySub){
            if(e.getSubject().getName().equals(selectedSub)){
                notFound = true;
            }
        }
        assertTrue(notFound);
    }

    @Ignore
    @Test
    public void test6_SearchByDescription() {
        tfSearchExam = lookup("#tfSearchExam").query();
        tvExam = lookup("#tvExam").query();
        clickOn("#cbSearchCriteria");
        type(KeyCode.DOWN);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#tfSearchExam");
        write("ABCD Ex");
        clickOn("#btnSearchExam");
        List<Exam> searchedByDesc = new ArrayList<>(tvExam.getItems());
        Boolean notFound = false;
        for(Exam e : searchedByDesc){
           if(e.getDescription().contains("ABCD Ex")){
                notFound = true;
            }
        }
        assertTrue(notFound);
    }

}
