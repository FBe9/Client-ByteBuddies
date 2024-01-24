/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subjectView;

import java.util.Date;
import java.util.concurrent.TimeoutException;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import main.SubjectMain;
import models.LanguageType;
import models.LevelType;
import models.Subject;
import models.Teacher;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

/**
 *
 * @author irati
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SubjectWindowControllerTest extends ApplicationTest {

    private TableView<Subject> tbSubjects;
    private TableColumn<Subject, String> tbColNameSub;
    private TableColumn<Subject, ObservableSet<Teacher>> tbColTeachersSub;
    private TableColumn<Subject, LevelType> tbColLevelSub;
    private TableColumn<Subject, LanguageType> tbColLanguageSub;
    private TableColumn<Subject, Date> tbColInitDateSub;
    private TableColumn<Subject, Date> tbColEndDateSub;
    private TableColumn<Subject, String> tbColHoursSub;
    private TableColumn tbColMatriculated;
    private TableColumn<Subject, String> tbColStudents;
    private TextField tfSearchSubject;
    private ComboBox cbSearchSubject;
    private Button btnSearchSubject;
    private Button btnCreateSubject;
    private Button btnDeleteSubject;
    private Button btnPrintSubject;
    private DatePicker dpDateSearchSubject;
    private TableColumn<Subject, String> tbColUnits;
    private TableColumn<Subject, String> tbColExams;

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(SubjectMain.class);
        
    }
}
