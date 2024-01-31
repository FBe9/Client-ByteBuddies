package examView;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.ExamMain;
import models.Subject;
import models.User;
import static org.junit.Assert.fail;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import view.exam.ExamWindowController;

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
        
        
    }

    @Ignore
    @Test
    public void testSetStage() {
        System.out.println("setStage");
        Stage stage = null;
        ExamWindowController instance = new ExamWindowController();
        instance.setStage(stage);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetUser() {
        System.out.println("setUser");
        User user = null;
        ExamWindowController instance = new ExamWindowController();
        instance.setUser(user);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetCurrentSubject() {
        System.out.println("setCurrentSubject");
        Subject subject = null;
        ExamWindowController instance = new ExamWindowController();
        instance.setCurrentSubject(subject);
        fail("The test case is a prototype.");
    }

    @Test
    public void testCreateNewRow() {
        System.out.println("createNewRow");
        ExamWindowController instance = new ExamWindowController();
        instance.createNewRow();
        fail("The test case is a prototype.");
    }

    @Test
    public void testDeleteExam() {
        System.out.println("deleteExam");
        ExamWindowController instance = new ExamWindowController();
        instance.deleteExam();
        fail("The test case is a prototype.");
    }

    @Test
    public void testPrintReport() {
        System.out.println("printReport");
        ExamWindowController instance = new ExamWindowController();
        instance.printReport();
        fail("The test case is a prototype.");
    }

}
