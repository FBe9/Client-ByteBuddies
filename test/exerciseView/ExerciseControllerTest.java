package exerciseView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import main.ExerciseMain;
import models.Exercise;
import models.LevelType;
import models.Unit;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 * Class for testing Exercise window.
 *
 * @author Leire
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExerciseControllerTest extends ApplicationTest {

    /**
     * Exercise data table view.
     *
     */
    private TableView<Exercise> tvExercise;

    /**
     * Exercise number data table column. Exercise description data table
     * column. Exercise file data table column. Exercise file solution data
     * table column. Exercise deadline data table column. Exercise hours data
     * table column.
     *
     */
    private TableColumn tcNumber, tcDescription, tcFile, tcFileSolution, tcDeadline, tcHours;

    /**
     * Exercise unit data table column.
     *
     */
    private TableColumn<Exercise, Unit> tcUnit;

    /**
     * Exercise level type data table column.
     */
    private TableColumn<Exercise, LevelType> tcLevelType;

    /**
     * Exercise search UI text field. Exercise number UI text field. Exercise
     * description UI text field. Exercise hours UI text field.
     *
     */
    private TextField tfSearch, tfNumber, tfDescription, tfHours;

    /**
     * Unit combo box for the search. Level type combo box for the search. Unit
     * combo box for the create and modify. Level type combo box for the create
     * and modify.
     *
     */
    private ComboBox cbUnitSearch, cbSearchType, cbUnitCreate, cbLevelTypeCreate;

    /**
     * Exercise deadline date picker.
     *
     */
    private DatePicker dpDeadline;

    /**
     * Search exercise data button. Delete exercise data button. Modify exercise
     * data button. Create exercise data button. Print exercise data button.
     * Send a file button. Send a file with the solution button. Receive a file
     * button. Receive a file solution button.
     *
     */
    private Button btmSearch, btmDelete, btmModify, btmCreate, btmPrint, btmFileSend, btmFileSolutionSend, btmFileReceive, btmFileSolutionReceive;

    /**
     * Create and Modify label error. If the user writes something other than
     * numbers in the numerical fields, a message will be added to the label
     * informing about this.
     */
    private Label lblErrorCreateModify;

    public ExerciseControllerTest() {
    }

    /**
     * The start method of the JavaFX application.
     *
     * @throws java.util.concurrent.TimeoutException If an exception occurs
     * during the start process.
     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(ExerciseMain.class);
    }

    /**
     * Method to create an exercise.
     */
    @Test
    @Ignore
    public void test1_createExercise() {
        tvExercise = lookup("#tvExercise").query();
        //get row count
        int rowCount = tvExercise.getItems().size();
        //llenar los campos
        clickOn("#tfNumber");
        write("9");

        clickOn("#tfHours");
        write("9");

        clickOn("#dpDeadline");
        write("10/10/2024");

        clickOn("#tfDescription");
        write("Create test");

        clickOn("#btmCreate");
        verifyThat("Created successfully", isVisible());
        clickOn("Aceptar");

        clickOn("#btmSearch");
        assertEquals(rowCount + 1, tvExercise.getItems().size());

        ObservableList<Exercise> items = tvExercise.getItems();
        int rowIndex = items.size() - 1;
        Exercise insertedExercise = items.get(rowIndex);

        //Verificar que la nueva fila tiene los mismos datos que la creada por defecto
        assertEquals(insertedExercise.getNumber(), "9");
        assertEquals(insertedExercise.getHours(), "9");
        assertEquals(insertedExercise.getDescription(), "Create test");
    }

    /**
     * Method to verify that the create button is disabled if not all fields are
     * filled out.
     */
    @Test
    @Ignore
    public void test1_createExerciseDisabled() {
        tvExercise = lookup("#tvExercise").query();
        //llenar los campo
        clickOn("#tfHours");
        write("9");

        clickOn("#dpDeadline");
        write("10/10/2024");

        clickOn("#tfDescription");
        write("Create test");

        verifyThat("#btmCreate", isDisabled());
    }

    /**
     * Method to verify that if there are no numbers in the tfNumber and tfHours
     * fields, the create button is disabled and a red message appears that will
     * be in a label.
     */
    @Test
    @Ignore
    public void test1_createExerciseErrorLabel() {
        tvExercise = lookup("#tvExercise").query();

        clickOn("#dpDeadline");
        write("10/10/2024");

        clickOn("#tfDescription");
        write("Create test");

        clickOn("#tfNumber");
        write("a");

        clickOn("#tfHours");
        write("a");

        verifyThat("🛈 Only numbers are allowed on the number and the hours fields.", isVisible());
        verifyThat("#btmCreate", isDisabled());

        clickOn("#tfNumber");
        eraseText(1);
        write("10");

        verifyThat("🛈 Only numbers are allowed on the number and the hours fields.", isVisible());
        verifyThat("#btmCreate", isDisabled());

        clickOn("#tfHours");
        eraseText(1);
        write("10");

        verifyThat("#btmCreate", isVisible());
    }

    /**
     * Method to edit an exercise.
     */
    @Test
    @Ignore
    public void test2_modifyExercise() {
        tvExercise = lookup("#tvExercise").query();
        ObservableList<Exercise> items = tvExercise.getItems();
        int rowIndex = items.size() - 1;
        Node row = lookup(".table-row-cell").nth(rowIndex).query();

        clickOn(row);

        clickOn("#tfNumber");
        eraseText(3);
        write("10");

        clickOn("#btmModify");
        verifyThat("Are you sure you want to modify this exercise?", isVisible());
        clickOn("Aceptar");
        verifyThat("Successfully modified", isVisible());
        clickOn("Aceptar");

        Exercise modifiedExercise = items.get(rowIndex);

        assertEquals(modifiedExercise.getNumber(), "10");
    }

    /**
     * Method to find exercises by the number.
     */
    @Test
    @Ignore
    public void test3_searchByNumber() {
        tvExercise = lookup("#tvExercise").query();
        tfSearch = lookup("#tfSearch").query();
        cbSearchType = lookup("#cbSearchType").query();
        //Se busca una asignatura que ya este en la tabla para buscar por ella.
        List<Exercise> allExercise = new ArrayList<>(tvExercise.getItems());
        String exerciseNumber = null;
        for (Exercise exercise : allExercise) {
            if (exercise.getNumber() != null) {
                exerciseNumber = exercise.getNumber();
                break;
            }
        }
        clickOn(cbSearchType);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);

        clickOn("#tfSearch");
        //write("1");
        write(exerciseNumber);
        clickOn("#btmSearch");
        //Mira después de la búsqueda que asignaturas hay y si encuentra alguna con ese nombre.
        List<Exercise> dataExercise = new ArrayList<>(tvExercise.getItems());
        for (Exercise exercise : dataExercise) {
            assertTrue(exercise.getNumber().contains(exerciseNumber));
        }
    }

    /**
     * Method to find exercises by the level type.
     */
    @Test
    @Ignore
    public void test3_searchByLevelType() {
        tvExercise = lookup("#tvExercise").query();
        tfSearch = lookup("#tfSearch").query();
        cbSearchType = lookup("#cbSearchType").query();
        //Se busca una asignatura que ya este en la tabla para buscar por ella.
        List<Exercise> allExercise = new ArrayList<>(tvExercise.getItems());
        LevelType exerciseLevel = null;
        for (Exercise exercise : allExercise) {
            if (exercise.getLevelType() != null) {
                exerciseLevel = exercise.getLevelType().BEGGINER;
                break;
            }
        }
        clickOn(cbSearchType);
        push(KeyCode.DOWN);

        clickOn("#tfSearch");
        write(exerciseLevel.toString());
        clickOn("#btmSearch");
        //Mira después de la búsqueda que asignaturas hay y si encuentra alguna con ese nombre.
        List<Exercise> dataExercise = new ArrayList<>(tvExercise.getItems());
        for (Exercise exercise : dataExercise) {
            assertEquals(exercise.getLevelType(), exerciseLevel);
            //assertTrue(exercise.getLevelType().compareTo(LevelType.BEGGINER).toString());
        }
    }

    /**
     * Method to find exercises by unit name.
     */
    @Test
    @Ignore
    public void test3_searchByUnit() {
        tvExercise = lookup("#tvExercise").query();
        cbUnitSearch = lookup("#cbUnitSearch").query();
        cbSearchType = lookup("#cbSearchType").query();
        btmSearch = lookup("#btmSearch").query();

        List<Exercise> allExercise = new ArrayList<>(tvExercise.getItems());
        String exerciseUnit = null;
        for (Exercise exercise : allExercise) {
            if (exercise.toString() != null) {
                exerciseUnit = exercise.toString();
                break;
            }
        }
        //Selecciona dentro del comboBox la búsqueda
        clickOn(cbSearchType);
        push(KeyCode.UP);
        push(KeyCode.UP);

        clickOn(btmSearch);

        List<Exercise> dataExercise = new ArrayList<>(tvExercise.getItems());
        for (Exercise exercise : dataExercise) {
            //assertEquals(exercise.toString(), exerciseUnit);
            //assertTrue(exercise.toString().equals(exerciseUnit));
        }
    }

    /**
     * Method to verify the message that appears when no exercise is found.
     */
    @Test
    @Ignore
    public void test3_searchExerciseDontExist() {
        cbUnitSearch = lookup("#cbUnitSearch").query();
        btmSearch = lookup("#btmSearch").query();

        //Selecciona dentro del comboBox la búsqueda
        clickOn(cbUnitSearch);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);

        clickOn(btmSearch);

        verifyThat("There is no Exercise with that All", isVisible());
        clickOn("Aceptar");
    }

    /**
     * Method to delete an exercise.
     */
    @Test
    @Ignore
    public void test4_deleteExercise() {
        tvExercise = lookup("#tvExercise").query();
        ObservableList<Exercise> items = tvExercise.getItems();
        int rowIndex = items.size() - 1;
        Node row = lookup(".table-row-cell").nth(rowIndex).query();

        clickOn(row);
        //Selecciona una asignatura para que se habilite el botón de borrado.
        clickOn(row);
        Exercise deleteExercise = (Exercise) tvExercise.getSelectionModel().getSelectedItem();
        String name = deleteExercise.getNumber();
        clickOn("#btmDelete");
        //Verifica que aparezca el mensaje de confirmación del borrado.
        verifyThat("Delete selected row?\n" + "This operation cannot be undone.", isVisible());
        clickOn("Aceptar");

        Node row2 = lookup(".table-row-cell").nth(rowIndex).query();
        clickOn(row2);

        Exercise deleteExercise2 = (Exercise) tvExercise.getSelectionModel().getSelectedItem();
        assertNotEquals(deleteExercise, deleteExercise2);

        List<Exercise> dataExercise = new ArrayList<>(tvExercise.getItems());
        Boolean notFound = true;
        for (Exercise exercise : dataExercise) {
            if (exercise.getNumber().equalsIgnoreCase(name)) {
                notFound = false;
            }
        }
        assertTrue(notFound);
    }
}
