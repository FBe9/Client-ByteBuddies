/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subjectView;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.SubjectMain;
import models.LanguageType;
import models.LevelType;
import models.Subject;
import models.Teacher;
import models.Unit;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.api.FxAssert.verifyThat;
import static org.junit.Assert.assertEquals;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;

/**
 *
 * @author irati
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SubjectWindowControllerTest extends ApplicationTest {

    private TableView<Subject> tableView;
    private TextField search;
    private ComboBox comboSearch;
    private DatePicker datePicker;
    private Button buttonSearch;
    private Button buttonDelete;
    private Button buttonImprimir;

    private TableView<Subject> tbSubjects;

    private TableColumn tbColNameSub;

    private TableColumn<Subject, ObservableSet<Teacher>> tbColTeachersSub;

    private TableColumn<Subject, LevelType> tbColLevelSub;

    private TableColumn<Subject, LanguageType> tbColLanguageSub;

    private TableColumn<Subject, Date> tbColInitDateSub;

    private TableColumn<Subject, Date> tbColEndDateSub;

    private TableColumn<Subject, String> tbColHoursSub;

    private TableColumn tbColMatriculated;

    private TableColumn<Subject, String> tbColStudents;

    @Override
    public void start(Stage stage) throws Exception {
        new SubjectMain().start(stage);

    }

    @Test
    public void test0_initialStage() {
        buttonSearch = lookup("#btnSearchSubject").query();
        datePicker = lookup("#dpDateSearchSubject").query();
        buttonDelete = lookup("#btnDeleteSubject").query();
        buttonImprimir = lookup("#btnPrintSubject").query();
        verifyThat(buttonSearch, isDisabled());
        verifyThat(datePicker, isInvisible());
        verifyThat(buttonDelete, isDisabled());
        verifyThat(buttonImprimir, isEnabled());
        // verifyThat(tbSubjects, isVisible());

    }

    @Test
    public void test1_insertSubject() {
        tableView = lookup("#tbSubjects").query();
        Integer count = tableView.getItems().size();
        clickOn("#btnCreateSubject");

        ObservableList<Subject> items = tableView.getItems();

        //Verifica a que haya una nueva fila en la tabla
        assertEquals(tableView.getItems().size(), count + 1);

        int rowIndex = items.size() - 1;
        Subject insertedSubject = items.get(rowIndex);

        //Pasar las fechas a LocalDate para compararlas
        Date dateInit = insertedSubject.getDateInit();
        LocalDate dateInitLocal = dateInit.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Date dateEnd = insertedSubject.getDateEnd();
        LocalDate dateEndLocal = dateEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        //Verificar que la nueva fila tiene los mismos datos que la creada por defecto
        assertEquals(insertedSubject.getName(), null);
        assertEquals(insertedSubject.getLevelType(), LevelType.BEGGINER);
        assertEquals(insertedSubject.getLanguageType(), LanguageType.SPANISH);
        assertEquals(dateInitLocal, LocalDate.now());
        assertEquals(dateEndLocal, LocalDate.now());
        assertEquals(insertedSubject.getHours(), null);
    }

    @Test
    public void test2_updateSubject() {
        tableView = lookup("#tbSubjects").query();
        int rowCount = tableView.getItems().size();
        Node row = lookup(".table-row-cell").nth(0).query();

        clickOn(row);

        Integer tablerow = tableView.getSelectionModel().getSelectedIndex();
        Node tableColumnName = lookup("#tbColNameSub").nth(tablerow + 1).query();
        Node tableColumTeachers = lookup("#tbColTeachersSub").nth(tablerow + 1).query();
        Node tableColumnLevel = lookup("#tbColLevelSub").nth(tablerow + 1).query();
        Node tableColumnLanguage = lookup("#tbColLanguageSub").nth(tablerow + 1).query();
        Node tableColumnInitDate = lookup("#tbColInitDateSub").nth(tablerow + 1).query();
        Node tableColumnEndDate = lookup("#tbColEndDateSub").nth(tablerow + 1).query();
        Node tableColumnHours = lookup("#tbColHoursSub").nth(tablerow + 1).query();

        Subject subjectSelected = (Subject) tableView.getSelectionModel().getSelectedItem();

        String name = subjectSelected.getName();
        Set<Teacher> originalTeachers = subjectSelected.getTeachers();
        List<Teacher> originalTeacherList = new ArrayList<>(originalTeachers);
        LevelType levelType = subjectSelected.getLevelType();
        LanguageType languageType = subjectSelected.getLanguageType();
        String hours = subjectSelected.getHours();

        Date dateInit = subjectSelected.getDateInit();
        LocalDate dateInitLocal = dateInit.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Date dateEnd = subjectSelected.getDateEnd();
        LocalDate dateEndLocal = dateEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        clickOn(tableColumnName);
        write("AAA");
        push(KeyCode.ENTER);

        doubleClickOn(tableColumTeachers);
        push(KeyCode.DOWN);
        push(KeyCode.ENTER);

        doubleClickOn(tableColumnLevel);
        if (levelType.equals(LevelType.EXPERIENCED)) {
            push(KeyCode.UP);
        } else {
            push(KeyCode.DOWN);
        }

        doubleClickOn(tableColumnLanguage);
        if (languageType.equals(LanguageType.ENGLISH)) {
            push(KeyCode.UP);
        } else {
            push(KeyCode.DOWN);
        }

        doubleClickOn(tableColumnInitDate);
        clickOn(tableColumnInitDate);
        write("18/11/2023");
        press(KeyCode.ENTER);
        press(KeyCode.ENTER);

        doubleClickOn(tableColumnEndDate);
        clickOn(tableColumnEndDate);
        write("18/02/2024");
        type(KeyCode.ENTER);

        clickOn(tableColumnHours);
        write("55");
        type(KeyCode.ENTER);

        Subject modifiedSubject = (Subject) tableView.getSelectionModel().getSelectedItem();
        Date dateInitMo = modifiedSubject.getDateInit();
        LocalDate dateInitLocalMo = dateInitMo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1);
        Date dateEndMo = modifiedSubject.getDateEnd();
        LocalDate dateEndLocalMo = dateEndMo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1);

        Set<Teacher> modifiedTeachers = modifiedSubject.getTeachers();
        List<Teacher> modifiedTeacherList = new ArrayList<>(modifiedTeachers);

        assertNotEquals(name, modifiedSubject.getName());
        assertNotEquals(originalTeacherList, modifiedTeacherList);
        assertNotEquals(levelType, modifiedSubject.getLevelType());
        assertNotEquals(languageType, modifiedSubject.getLanguageType());
        assertNotEquals(dateEndLocal, dateEndLocalMo);
        assertNotEquals(dateInitLocal, dateInitLocalMo);
        assertNotEquals(hours, modifiedSubject.getHours());

    }

    @Test
    public void test3_searchByName() {
        tableView = lookup("#tbSubjects").query();
        search = lookup("#tfSearchSubject").query();

        clickOn(search);
        write("AAA");
        press(KeyCode.ENTER);

        List<Subject> dataSubject = new ArrayList<>(tableView.getItems());
        for (Subject subject : dataSubject) {
            assertTrue(subject.getName().contains("AAA"));
        }

    }

    @Test
    public void test4_searchByStartDate() {
        tableView = lookup("#tbSubjects").query();
        datePicker = lookup("#dpDateSearchSubject").query();
        buttonSearch = lookup("#btnSearchSubject").query();
        comboSearch = lookup("#cbSearchSubject").query();
        clickOn(comboSearch);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        press(KeyCode.ENTER);
        clickOn(datePicker);
        write("18/11/2023");
        clickOn(buttonSearch);
        press(KeyCode.ENTER);

        List<Subject> dataSubject = new ArrayList<>(tableView.getItems());
        for (Subject subject : dataSubject) {
            Date dateInit = subject.getDateInit();
            Instant instant = dateInit.toInstant();
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

            String formattedDate = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            assertTrue(formattedDate.contains("18-11-2023"));
        }
    }

    @Test
    public void test5_searchByEndDate() {
        tableView = lookup("#tbSubjects").query();
        datePicker = lookup("#dpDateSearchSubject").query();
        comboSearch = lookup("#cbSearchSubject").query();
        buttonSearch = lookup("#btnSearchSubject").query();
        clickOn(comboSearch);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        press(KeyCode.ENTER);
        clickOn(datePicker);
        write("18/02/2024");
        clickOn(buttonSearch);

        List<Subject> dataSubject = new ArrayList<>(tableView.getItems());
        for (Subject subject : dataSubject) {
            Date dateEnd = subject.getDateEnd();
            Instant instant = dateEnd.toInstant();
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

            String formattedDate = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            assertTrue(formattedDate.contains("18-02-2024"));
        }
    }

    @Test
    public void test6_searchByTeacherName() {
        tableView = lookup("#tbSubjects").query();
        search = lookup("#tfSearchSubject").query();
        comboSearch = lookup("#cbSearchSubject").query();
        buttonSearch = lookup("#btnSearchSubject").query();
        clickOn(comboSearch);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        press(KeyCode.ENTER);
        clickOn(search);
        write("Carmen");
        clickOn(buttonSearch);

        List<Subject> dataSubject = new ArrayList<>(tableView.getItems());
        for (Subject subject : dataSubject) {
            assertTrue(subject.getTeachers().toString().contains("Carmen"));
        }
    }

    @Test
    public void test7_searchByEnrolledStudents() {
        tableView = lookup("#tbSubjects").query();
        search = lookup("#tfSearchSubject").query();
        comboSearch = lookup("#cbSearchSubject").query();
        buttonSearch = lookup("#btnSearchSubject").query();
        clickOn(comboSearch);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        press(KeyCode.ENTER);
        clickOn(search);
        write("1");
        clickOn(buttonSearch);

        List<Subject> dataSubject = new ArrayList<>(tableView.getItems());
        for (Subject subject : dataSubject) {
            assertTrue(subject.getStudentsCount() >= 1);
        }
    }

    @Test
    public void test8_searchByUnit() {
        tableView = lookup("#tbSubjects").query();
        ComboBox combo = lookup("#cbSearchSubject").query();
        buttonSearch = lookup("#btnSearchSubject").query();
        Node row = lookup(".table-row-cell").nth(0).query();
        search = lookup("#tfSearchSubject").query();

        clickOn(combo);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        clickOn(search);
        write("1");
        clickOn(buttonSearch);
        clickOn(row);

        Integer tablerow = tableView.getSelectionModel().getSelectedIndex();
        Subject subjectSelected = (Subject) tableView.getSelectionModel().getSelectedItem();
        String subjectName = subjectSelected.getName();
        Node tableColumnUnit = lookup("#tbColUnits").nth(tablerow + 1).query();
        clickOn(tableColumnUnit);
        TableView<Unit> tableViewUnit = lookup("#tbvUnit").query();
        List<Unit> dataUnit = new ArrayList<>(tableViewUnit.getItems());
        for (Unit unit : dataUnit) {
            assertTrue(unit.getSubject().getName().equals(subjectName));
        }

    }

    @Test
    public void test9_deleteSubject() {
        Node tableColumnName = lookup("#tbColNameSub").nth(0).query();
        clickOn(tableColumnName);
        tableView = lookup("#tbSubjects").query();
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        Subject subjectDelete = (Subject) tableView.getSelectionModel().getSelectedItem();
        clickOn("#btnDeleteSubject");
        verifyThat("Do you want to delete the subject " + subjectDelete.getName() + " ?", isVisible());
        press(KeyCode.ENTER);
        Node row2 = lookup(".table-row-cell").nth(0).query();
        clickOn(row2);
        Subject subjectDeleteAfter = (Subject) tableView.getSelectionModel().getSelectedItem();
        assertNotEquals(subjectDelete, subjectDeleteAfter);

    }

}
