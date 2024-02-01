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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.api.FxAssert.verifyThat;
import static org.junit.Assert.assertEquals;

/**
 * Class for testing Subject window from Teacher POV --> CRUD.
 *
 * @author irati
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SubjectWindowControllerTest extends ApplicationTest {

    /**
     * TableView used in the user interface to display subjects.
     */
    private TableView<Subject> tableView;

    /**
     * TextField used for performing searches.
     */
    private TextField search;

    /**
     * ComboBox used for specific selections.
     */
    private ComboBox comboSearch;

    /**
     * DatePicker used in the user interface.
     */
    private DatePicker datePicker;

    /**
     * Button used to initiate searches or perform specific actions.
     */
    private Button buttonSearch;

    /**
     * The start method of the JavaFX application.
     *
     * @param stage The primary stage for the application.
     * @throws Exception If an exception occurs during the start process.
     */
    @Override
    public void start(Stage stage) throws Exception {
        new SubjectMain().start(stage);

    }

    /**
     * Method to create a subject.
     */
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

    /**
     * Method to edit a subject.
     */
    @Test
    public void test2_updateSubject() {
        tableView = lookup("#tbSubjects").query();
        Node row = lookup(".table-row-cell").nth(0).query();

        clickOn(row);
        //Seleccionar el nodo de todas las filas para poder hacer click en ella.
        Integer tablerow = tableView.getSelectionModel().getSelectedIndex();
        Node tableColumnName = lookup("#tbColNameSub").nth(tablerow + 1).query();
        Node tableColumTeachers = lookup("#tbColTeachersSub").nth(tablerow + 1).query();
        Node tableColumnLevel = lookup("#tbColLevelSub").nth(tablerow + 1).query();
        Node tableColumnLanguage = lookup("#tbColLanguageSub").nth(tablerow + 1).query();
        Node tableColumnInitDate = lookup("#tbColInitDateSub").nth(tablerow + 1).query();
        Node tableColumnEndDate = lookup("#tbColEndDateSub").nth(tablerow + 1).query();
        Node tableColumnHours = lookup("#tbColHoursSub").nth(tablerow + 1).query();

        //Coger los valores de la asignatura seleccionada antes de ser modificada.
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

        //Click en las diferentes celdas para ir modificando los datos
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
        write("134");
        type(KeyCode.ENTER);

        //Coger los valores de la asignatura modificada.
        Subject modifiedSubject = (Subject) tableView.getSelectionModel().getSelectedItem();
        Date dateInitMo = modifiedSubject.getDateInit();
        LocalDate dateInitLocalMo = dateInitMo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1);
        Date dateEndMo = modifiedSubject.getDateEnd();
        LocalDate dateEndLocalMo = dateEndMo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1);

        Set<Teacher> modifiedTeachers = modifiedSubject.getTeachers();
        List<Teacher> modifiedTeacherList = new ArrayList<>(modifiedTeachers);

        //Verificar que los valores no son iguales
        assertNotEquals(name, modifiedSubject.getName());
        assertNotEquals(originalTeacherList, modifiedTeacherList);
        assertNotEquals(levelType, modifiedSubject.getLevelType());
        assertNotEquals(languageType, modifiedSubject.getLanguageType());
        assertNotEquals(dateEndLocal, dateEndLocalMo);
        assertNotEquals(dateInitLocal, dateInitLocalMo);
        assertNotEquals(hours, modifiedSubject.getHours());

    }

    /**
     * Method to find a subject by its name.
     */
    @Test
    public void test3_searchByName() {
        tableView = lookup("#tbSubjects").query();
        search = lookup("#tfSearchSubject").query();
        //Se busca una asignatura que ya este en la tabla para buscar por ella.
        List<Subject> allSubjects = new ArrayList<>(tableView.getItems());
        String subjectName = null;
        for (Subject subject : allSubjects) {
            if (subject.getName() != null) {
                subjectName = subject.getName();
                break;
            }
        }
        clickOn(search);
        write(subjectName);
        press(KeyCode.ENTER);
        //Mira después de la búsqueda que asignaturas hay y si encuentra alguna con ese nombre.
        List<Subject> dataSubject = new ArrayList<>(tableView.getItems());
        for (Subject subject : dataSubject) {
            assertTrue(subject.getName().contains(subjectName));
        }
    }

    /**
     * Method to find by start date.
     */
    @Test
    public void test4_searchByStartDate() {
        tableView = lookup("#tbSubjects").query();
        datePicker = lookup("#dpDateSearchSubject").query();
        buttonSearch = lookup("#btnSearchSubject").query();
        comboSearch = lookup("#cbSearchSubject").query();
        //Selecciona dentro del comboBox la búsqueda
        clickOn(comboSearch);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        press(KeyCode.ENTER);
        clickOn(datePicker);
        //Inserta una búsqueda por fecha
        write("18/11/2023");
        clickOn(buttonSearch);
        press(KeyCode.ENTER);
        //Mira que esa fecha este dentro de las asignaturas que aparecen despues de hacer la búsqueda
        List<Subject> dataSubject = new ArrayList<>(tableView.getItems());
        for (Subject subject : dataSubject) {
            //Lo pasa a localdate para poder compararlo.
            Date dateInit = subject.getDateInit();
            Instant instant = dateInit.toInstant();
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

            String formattedDate = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            assertTrue(formattedDate.contains("18-11-2023"));
        }
    }

    /**
     * Method to find by end date.
     */
    @Test
    public void test5_searchByEndDate() {
        tableView = lookup("#tbSubjects").query();
        datePicker = lookup("#dpDateSearchSubject").query();
        comboSearch = lookup("#cbSearchSubject").query();
        buttonSearch = lookup("#btnSearchSubject").query();
        //Selecciona dentro del comboBox la búsqueda
        clickOn(comboSearch);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        press(KeyCode.ENTER);
        clickOn(datePicker);
        //Inserta una búsqueda por fecha
        write("18/02/2024");
        clickOn(buttonSearch);

        List<Subject> dataSubject = new ArrayList<>(tableView.getItems());
        //Mira que esa fecha este dentro de las asignaturas que aparecen despues de hacer la búsqueda
        for (Subject subject : dataSubject) {
            //Lo pasa a localdate para poder compararlo.
            Date dateEnd = subject.getDateEnd();
            Instant instant = dateEnd.toInstant();
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

            String formattedDate = localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            assertTrue(formattedDate.contains("18-02-2024"));
        }
    }

    /**
     * Method to find by teachers name
     */
    @Test
    public void test6_searchByTeacherName() {
        tableView = lookup("#tbSubjects").query();
        search = lookup("#tfSearchSubject").query();
        comboSearch = lookup("#cbSearchSubject").query();
        buttonSearch = lookup("#btnSearchSubject").query();
        //Selecciona dentro del comboBox la búsqueda
        clickOn(comboSearch);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        press(KeyCode.ENTER);
        clickOn(search);
        //Busca el nombre de un profesor
        write("Carmen");
        clickOn(buttonSearch);
        ////Mira que el nombre del profesor este dentro de las asignaturas que aparecen despues de hacer la búsqueda
        List<Subject> dataSubject = new ArrayList<>(tableView.getItems());
        for (Subject subject : dataSubject) {
            assertTrue(subject.getTeachers().toString().contains("Carmen"));
        }
    }

    /**
     * Method to find by number of students matriculated.
     */
    @Test
    public void test7_searchByEnrolledStudents() {
        tableView = lookup("#tbSubjects").query();
        search = lookup("#tfSearchSubject").query();
        comboSearch = lookup("#cbSearchSubject").query();
        buttonSearch = lookup("#btnSearchSubject").query();
        //Selecciona dentro del comboBox la búsqueda
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
        //Mira que haya ese número de matriculados en el studentsCount
        List<Subject> dataSubject = new ArrayList<>(tableView.getItems());
        for (Subject subject : dataSubject) {
            assertTrue(subject.getStudentsCount() >= 1);
        }
    }

    /**
     * Method to find by unit.
     */
    @Test
    public void test8_searchByUnit() {
        tableView = lookup("#tbSubjects").query();
        ComboBox combo = lookup("#cbSearchSubject").query();
        buttonSearch = lookup("#btnSearchSubject").query();
        Node row = lookup(".table-row-cell").nth(0).query();
        search = lookup("#tfSearchSubject").query();
        //Selecciona dentro del comboBox la búsqueda
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
        //Coge la posición de la columna units para abrir su ventana y verificar que se estan
        //viendo las asignaturas con ese nombre.
        Integer tablerow = tableView.getSelectionModel().getSelectedIndex();
        Subject subjectSelected = (Subject) tableView.getSelectionModel().getSelectedItem();
        String subjectName = subjectSelected.getName();
        Node tableColumnUnit = lookup("#tbColUnits").nth(tablerow + 1).query();
        clickOn(tableColumnUnit);
        TableView<Unit> tableViewUnit = lookup("#tbvUnit").query();
        List<Unit> dataUnit = new ArrayList<>(tableViewUnit.getItems());
        Integer count = 0;
        for (Unit unit : dataUnit) {
            assertTrue(unit.getSubject().getName().equals(subjectName));
            count = count + 1;
        }
        //Verifica que el número de veces que aparece la asignatura sea mayor o igual al número introducido.
        assertTrue(count >= 1);
    }

    /**
     * Method to delete a subject.
     */
    @Test
    public void test9_deleteSubject() {
        Node tableColumnName = lookup("#tbColNameSub").nth(0).query();
        clickOn(tableColumnName);
        tableView = lookup("#tbSubjects").query();
        Node row = lookup(".table-row-cell").nth(0).query();
        //Selecciona una asignatura para que se habilite el botón de borrado.
        clickOn(row);
        Subject subjectDelete = (Subject) tableView.getSelectionModel().getSelectedItem();
        String name = subjectDelete.getName();
        clickOn("#btnDeleteSubject");
        //Verifica que aparezca el mensaje de confirmación del borrado.
        verifyThat("Do you want to delete the subject " + subjectDelete.getName() + " ?", isVisible());
        press(KeyCode.ENTER);
        Node row2 = lookup(".table-row-cell").nth(0).query();
        clickOn(row2);
        Subject subjectDeleteAfter = (Subject) tableView.getSelectionModel().getSelectedItem();
        assertNotEquals(subjectDelete, subjectDeleteAfter);

        List<Subject> dataSubject = new ArrayList<>(tableView.getItems());
        Boolean notFound = true;
        for (Subject subject : dataSubject) {
            if (subject.getName().equalsIgnoreCase(name)) {
                notFound = false;
            }
        }

        assertTrue(notFound);

    }

}
