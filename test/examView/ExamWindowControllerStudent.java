package examView;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.ExamMain;
import models.Exam;
import static org.junit.Assert.assertTrue;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.framework.junit.ApplicationTest;

/**
 *
 * @author Alex
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExamWindowControllerStudent extends ApplicationTest {
    
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

    //@Ignore
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
