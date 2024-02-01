package view.unit;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.UnitMain;
import models.Subject;
import models.Unit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;
import org.junit.Test;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author Nerea
 */
public class UnitWindowControllerTest extends ApplicationTest {

    @FXML
    private TableView<Unit> tbvUnit;
    @FXML
    private TableColumn<Unit, String> tbcName;
    @FXML
    private TableColumn<Unit, Subject> tbcSubject;
    @FXML
    private TableColumn<Unit, String> tbcDescription;
    @FXML
    private TableColumn<Unit, Date> tbcDateInit;
    @FXML
    private TableColumn<Unit, Date> tbcDateEnd;
    @FXML
    private TableColumn<Unit, String> tbcHours;
    @FXML
    private TableColumn<Unit, String> tbcExercises;
    @FXML
    private TextField tfSearch;
    @FXML
    private ComboBox cbSubjects;
    @FXML
    private ComboBox cbSearchType;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnDeleteUnit;
    @FXML
    private Button btnCreateUnit;
    @FXML
    private Button btnPrint;
    @FXML
    private DatePicker dpSearch;

    public UnitWindowControllerTest() {
    }

    /**
     * The start method of the JavaFX application.
     *
     * @param stage The primary stage for the application.
     * @throws Exception If an exception occurs during the start process.
     */
    @Override
    public void start(Stage stage) throws Exception {
        new UnitMain().start(stage);

    }

    /**
     * Test of handelCreateButtonAction method, of class UnitWindowController.
     */
    @Ignore
    @Test
    public void A_testHandelCreateButtonAction() {
        tbvUnit = lookup("#tbvUnit").query();
        cbSubjects = lookup("#cbSubjects").query();
        clickOn(cbSubjects);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        press(KeyCode.ENTER);
        clickOn("#btnCreateUnit");
        verifyThat("Unit added successfully in ADT subject", isVisible());
        press(KeyCode.ENTER);
        Integer count = tbvUnit.getItems().size();
        ObservableList<Unit> userUnits = tbvUnit.getItems();

        //Hay una fila de más en la tabla despues de usar el metodo.
        //assertEquals(count + 1, tbvUnit.getItems().size());
        Integer unitRow = 0;
        Unit newUnit = new Unit();
        for (int i = 0; i < userUnits.size(); i++) {
            if (userUnits.get(i).getName().equalsIgnoreCase("")) {
                if (userUnits.get(i).getSubject().getName().equalsIgnoreCase((String) cbSubjects.getSelectionModel().getSelectedItem())) {
                    newUnit = userUnits.get(i);
                    unitRow = tbvUnit.getItems().indexOf(newUnit);
                    i = userUnits.size();
                }
            }
        }

        //Pasar las fechas a LocalDate para compararlas
        /*Date dateInit = newUnit.getDateInit();
        LocalDate dateInitLocal = dateInit.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Date dateEnd = newUnit.getDateEnd();
        LocalDate dateEndLocal = dateEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();*/
        //Verificar que la nueva fila tiene los mismos datos que la creada por defecto
        assertEquals(null, newUnit.getName());
        assertEquals("ADT", newUnit.getSubject().getName());
        assertEquals(null, newUnit.getDescription());
        // assertEquals(LocalDate.now(), dateInitLocal);
        // assertEquals(LocalDate.now(), dateEndLocal);
        assertEquals("0", newUnit.getHours());

        //Borrar prueba en la trabla
        Node row = lookup(".table-row-cell").nth(unitRow).query();
        clickOn(row);
        clickOn("#btnDeleteUnit");
    }

    /**
     * Test of search by name method, of class UnitWindowController.
     */
    @Ignore
    @Test
    public void B_testHandelSearchName() {
        cbSubjects = lookup("#cbSubjects").query();
        cbSearchType = lookup("#cbSearchType").query();
        tfSearch = lookup("#tfSearch").query();
        btnSearch = lookup("#btnSearch").query();
        tbvUnit = lookup("#tbvUnit").query();
        clickOn(cbSubjects);
        push(KeyCode.DOWN);
        press(KeyCode.ENTER);
        clickOn(tfSearch);
        write("UD");
        clickOn(btnSearch);
        ObservableList<Unit> userUnits = tbvUnit.getItems();
        for (Unit unit : userUnits) {
            assertTrue(unit.getName().contains("UD"));
        }
    }

    /**
     * Test of search by date init method, of class UnitWindowController.
     */
    @Ignore
    @Test
    public void C_testHandelSearchDateInit() {
        cbSubjects = lookup("#cbSubjects").query();
        cbSearchType = lookup("#cbSearchType").query();
        dpSearch = lookup("#dpSearch").query();
        btnSearch = lookup("#btnSearch").query();
        tbvUnit = lookup("#tbvUnit").query();
        clickOn(cbSubjects);
        push(KeyCode.DOWN);
        press(KeyCode.ENTER);
        clickOn(cbSearchType);
        push(KeyCode.DOWN);
        press(KeyCode.ENTER);
        clickOn(dpSearch);
        write("31-ene-2024");
        clickOn(btnSearch);
        ObservableList<Unit> userUnits = tbvUnit.getItems();
        for (Unit unit : userUnits) {
            //Lo pasa a localdate para poder compararlo.

        }
    }

    /**
     * Test of search by date end method, of class UnitWindowController.
     */
    @Ignore
    @Test
    public void D_testHandelSearchDateEnd() {
        cbSubjects = lookup("#cbSubjects").query();
        cbSearchType = lookup("#cbSearchType").query();
        dpSearch = lookup("#dpSearch").query();
        btnSearch = lookup("#btnSearch").query();
        tbvUnit = lookup("#tbvUnit").query();
        clickOn(cbSubjects);
        push(KeyCode.DOWN);
        press(KeyCode.ENTER);
        clickOn(cbSearchType);
        push(KeyCode.DOWN);
        press(KeyCode.ENTER);
        clickOn(dpSearch);
        write("31-ene-2024");
        clickOn(btnSearch);
        ObservableList<Unit> userUnits = tbvUnit.getItems();
        for (Unit unit : userUnits) {
            //Lo pasa a localdate para poder compararlo.

        }
    }

    /**
     * Test of search by hours method, of class UnitWindowController.
     */
    @Ignore
    @Test
    public void E_testHandelSearchHours() {
        cbSubjects = lookup("#cbSubjects").query();
        cbSearchType = lookup("#cbSearchType").query();
        tfSearch = lookup("#tfSearch").query();
        btnSearch = lookup("#btnSearch").query();
        tbvUnit = lookup("#tbvUnit").query();
        clickOn(cbSubjects);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        press(KeyCode.ENTER);
        clickOn(cbSearchType);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        press(KeyCode.ENTER);
        clickOn(tfSearch);
        write("2");
        clickOn(btnSearch);
        ObservableList<Unit> userUnits = tbvUnit.getItems();
        for (Unit unit : userUnits) {
            //Lo pasa a localdate para poder compararlo.
            assertEquals(unit.getHours(), "2");
        }
    }

    /**
     * Test of handelDeleteButtonAction method, of class UnitWindowController.
     */
    @Ignore
    @Test
    public void F_testHandelDeleteButtonAction() {
        tbvUnit = lookup("#tbvUnit").query();
        Integer roxNum = tbvUnit.getItems().size();
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        Unit deletedUnit = tbvUnit.getSelectionModel().getSelectedItem();
        clickOn("#btnDeleteUnit");
        verifyThat("¿Are you sure you want to delete this unit?", isVisible());
        press(KeyCode.ENTER);
        Node row2 = lookup(".table-row-cell").nth(0).query();
        clickOn(row2);
        Unit compareUnit = tbvUnit.getSelectionModel().getSelectedItem();
        assertNotEquals(compareUnit.getId(), deletedUnit.getId());
        ObservableList<Unit> userUnits = tbvUnit.getItems();
        Boolean notFound = true;
        for (Unit unit : userUnits) {
            if (unit.getName().equalsIgnoreCase(deletedUnit.getName())) {
                if (unit.getSubject().equals(deletedUnit.getSubject())) {
                    notFound = false;
                }
            }
        }
        assertTrue(notFound);
    }

    /**
     * Test of update name method, of class UnitWindowController.
     */
    //@Ignore
    @Test
    public void G_testHandelUpdateName() {
        tbvUnit = lookup("#tbvUnit").query();
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        //Seleccionar el nodo de todas las filas para poder hacer click en ella.
        Integer tablerow = tbvUnit.getSelectionModel().getSelectedIndex();
        Node tbcNameNode = lookup("#tbcName").nth(tablerow + 1).query();
        Node tbcSubjectNode = lookup("#tbcSubject").nth(tablerow + 1).query();
        Node tbcDescriptionNode = lookup("#tbcDescription").nth(tablerow + 1).query();
        Node tbcDateInitNode = lookup("#tbcDateInit").nth(tablerow + 1).query();
        Node tbcDateEndNode = lookup("#tbcDateEnd").nth(tablerow + 1).query();
        Node tbcHoursNode = lookup("#tbcHours").nth(tablerow + 1).query();

        Unit unitSelected = (Unit) tbvUnit.getSelectionModel().getSelectedItem();
        String name = unitSelected.getName();
        Subject subject = unitSelected.getSubject();
        String description = unitSelected.getDescription();
        String hours = unitSelected.getHours();

        //Click en las diferentes celdas para ir modificando los datos
        clickOn(tbcNameNode);
        write("prueba");
        push(KeyCode.ENTER);
        doubleClickOn(tbcSubjectNode);
        push(KeyCode.DOWN);
        clickOn(tbcDescriptionNode);
        write("prueba desc");
        push(KeyCode.ENTER);
        clickOn(tbcHoursNode);
        write("999");
        push(KeyCode.ENTER);
        doubleClickOn(tbcSubjectNode);
        push(KeyCode.DOWN);
        Unit modifiedUnit = (Unit) tbvUnit.getSelectionModel().getSelectedItem();

        assertNotEquals(name, modifiedUnit.getName());
        assertNotEquals(subject.getName(), modifiedUnit.getSubject().getName());
        assertNotEquals(description, modifiedUnit.getDescription());
        //date
        assertNotEquals(hours, modifiedUnit.getHours());

    }

}
