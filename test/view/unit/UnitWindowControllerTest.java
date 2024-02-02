package view.unit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        Integer count = tbvUnit.getItems().size();
        clickOn("#btnCreateUnit");
        String subject = (String) cbSubjects.getSelectionModel().getSelectedItem();
        verifyThat("UnitverifyT added successfully at " + subject + " subject", isVisible());
        clickOn("Aceptar");
        ObservableList<Unit> userUnits = tbvUnit.getItems();

        //Hay una fila de más en la tabla despues de usar el metodo.
        assertEquals(count + 1, tbvUnit.getItems().size());
        Integer unitRow = 0;
        Unit newUnit = new Unit();
        for (int i = 0; i < userUnits.size(); i++) {
            if (userUnits.get(i).getName().equalsIgnoreCase("")) {
                if (userUnits.get(i).getSubject().getName().equalsIgnoreCase(subject)) {
                    newUnit = userUnits.get(i);
                    unitRow = i;
                    i = userUnits.size();
                }
            }
        }

        //Pasar las fechas a LocalDate para compararlas
        Date dateInit = newUnit.getDateInit();
        LocalDate dateInitLocal = dateInit.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Date dateEnd = newUnit.getDateEnd();
        LocalDate dateEndLocal = dateEnd.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        //Verificar que la nueva fila tiene los mismos datos que la creada por defecto
        assertEquals("", newUnit.getName());
        assertEquals(subject, newUnit.getSubject().getName());
        assertEquals("", newUnit.getDescription());
        assertEquals(LocalDate.now(), dateInitLocal);
        assertEquals(LocalDate.now(), dateEndLocal);
        assertEquals("0", newUnit.getHours());

        //Borrar prueba en la trabla
        Node row = lookup(".table-row-cell").nth(unitRow).query();
        clickOn(row);
        clickOn("#btnDeleteUnit");
        clickOn("Aceptar");
    }

    /**
     * Test of handelCreateButtonAction method, of class UnitWindowController.
     */
    @Ignore
    @Test
    public void B_testHandelCreateNameExistException() {
        tbvUnit = lookup("#tbvUnit").query();
        cbSubjects = lookup("#cbSubjects").query();
        clickOn(cbSubjects);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        press(KeyCode.ENTER);
        Integer count = tbvUnit.getItems().size();
        clickOn("#btnCreateUnit");
        String subject = (String) cbSubjects.getSelectionModel().getSelectedItem();
        verifyThat("Unit added successfully at " + subject + " subject", isVisible());
        clickOn("Aceptar");
        ObservableList<Unit> userUnits = tbvUnit.getItems();
        //Hay una fila de más en la tabla despues de usar el metodo.
        assertEquals(count + 1, tbvUnit.getItems().size());
        clickOn("#btnCreateUnit");
        verifyThat("There is already a unit at " + subject + " subject without name, please change it before creating a new one on " + subject + " subject.", isVisible());
        clickOn("Aceptar");

        //Borrar prueba en la trabla
        Integer unitRow = 0;
        for (int i = 0; i < userUnits.size(); i++) {
            if (userUnits.get(i).getName().equalsIgnoreCase("")) {
                if (userUnits.get(i).getSubject().getName().equalsIgnoreCase(subject)) {
                    unitRow = i;
                    i = userUnits.size();
                }
            }
        }
        Node row = lookup(".table-row-cell").nth(unitRow).query();
        clickOn(row);
        clickOn("#btnDeleteUnit");
        clickOn("Aceptar");
    }

    /**
     * Test of search by name method, of class UnitWindowController.
     */
    @Ignore
    @Test
    public void C_testHandelSearchName() {
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
    public void D_testHandelSearchDateInit() {
        cbSubjects = lookup("#cbSubjects").query();
        cbSearchType = lookup("#cbSearchType").query();
        dpSearch = lookup("#dpSearch").query();
        btnSearch = lookup("#btnSearch").query();
        tbvUnit = lookup("#tbvUnit").query();
        clickOn(cbSubjects);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        press(KeyCode.ENTER);
        clickOn(cbSearchType);
        push(KeyCode.DOWN);
        press(KeyCode.ENTER);
        clickOn("#btnCreateUnit");
        clickOn("Aceptar");
        String subject = (String) cbSubjects.getSelectionModel().getSelectedItem();
        clickOn(".date-picker .arrow-button");
        clickOn(String.valueOf(LocalDate.now().getDayOfMonth()));
        LocalDate datePicker = dpSearch.getValue();
        Date date = Date.from(datePicker.atStartOfDay(ZoneId.systemDefault()).toInstant());
        clickOn(dpSearch);

        clickOn(btnSearch);
        ObservableList<Unit> userUnits = tbvUnit.getItems();
        for (Unit unit : userUnits) {
            //Lo pasa a localdate para poder compararlo.
            assertTrue(unit.getDateInit().equals(date));
        }

        //Borrar prueba en la trabla
        Integer unitRow = 0;
        for (int i = 0; i < userUnits.size(); i++) {
            if (userUnits.get(i).getName().equalsIgnoreCase("")) {
                if (userUnits.get(i).getSubject().getName().equalsIgnoreCase(subject)) {
                    unitRow = i;
                    i = userUnits.size();
                }
            }
        }
        Node row = lookup(".table-row-cell").nth(unitRow).query();
        clickOn(row);
        clickOn("#btnDeleteUnit");
        clickOn("Aceptar");
    }

    /**
     * Test of search by date end method, of class UnitWindowController.
     */
    @Ignore
    @Test
    public void E_testHandelSearchDateEnd() {
        cbSubjects = lookup("#cbSubjects").query();
        cbSearchType = lookup("#cbSearchType").query();
        dpSearch = lookup("#dpSearch").query();
        btnSearch = lookup("#btnSearch").query();
        tbvUnit = lookup("#tbvUnit").query();
        clickOn(cbSubjects);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        press(KeyCode.ENTER);
        clickOn(cbSearchType);
        push(KeyCode.DOWN);
        press(KeyCode.ENTER);
        clickOn("#btnCreateUnit");
        clickOn("Aceptar");
        String subject = (String) cbSubjects.getSelectionModel().getSelectedItem();
        clickOn(".date-picker .arrow-button");
        clickOn(String.valueOf(LocalDate.now().getDayOfMonth()));
        LocalDate datePicker = dpSearch.getValue();
        Date date = Date.from(datePicker.atStartOfDay(ZoneId.systemDefault()).toInstant());
        clickOn(dpSearch);

        clickOn(btnSearch);
        ObservableList<Unit> userUnits = tbvUnit.getItems();
        for (Unit unit : userUnits) {
            //Lo pasa a localdate para poder compararlo.
            assertTrue(unit.getDateEnd().equals(date));
        }

        //Borrar prueba en la trabla
        Integer unitRow = 0;
        for (int i = 0; i < userUnits.size(); i++) {
            if (userUnits.get(i).getName().equalsIgnoreCase("")) {
                if (userUnits.get(i).getSubject().getName().equalsIgnoreCase(subject)) {
                    unitRow = i;
                    i = userUnits.size();
                }
            }
        }
        Node row = lookup(".table-row-cell").nth(unitRow).query();
        clickOn(row);
        clickOn("#btnDeleteUnit");
        clickOn("Aceptar");
    }

    /**
     * Test of search by hours method, of class UnitWindowController.
     */
    @Ignore
    @Test
    public void F_testHandelSearchHours() {
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
    public void G_testHandelDeleteButtonAction() {
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
    @Ignore
    @Test
    public void H_testHandelUpdateName() {
        tbvUnit = lookup("#tbvUnit").query();
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        //Seleccionar el nodo de todas las filas para poder hacer click en ella.
        Integer tablerow = tbvUnit.getSelectionModel().getSelectedIndex();
        Node tbcNameNode = lookup("#tbcName").nth(tablerow + 1).query();

        Unit unitSelected = (Unit) tbvUnit.getSelectionModel().getSelectedItem();
        String name = unitSelected.getName();

        //Click en las diferentes celdas para ir modificando los datos
        clickOn(tbcNameNode);
        write("prueba");
        push(KeyCode.ENTER);

        Unit modifiedUnit = (Unit) tbvUnit.getSelectionModel().getSelectedItem();
        assertNotEquals(name, modifiedUnit.getName());

        //Devolverle su valor
        clickOn(tbcNameNode);
        write("prueba");
        push(KeyCode.ENTER);
    }

    /**
     * Test of update name INVALID method, Name Exist, of class
     * UnitWindowController.
     */
    @Ignore
    @Test
    public void I_testHandelUpdateNameNameExistException() {
        tbvUnit = lookup("#tbvUnit").query();
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        //Seleccionar el nodo de todas las filas para poder hacer click en ella.
        Integer tablerow = tbvUnit.getSelectionModel().getSelectedIndex();
        Node tbcNameNode = lookup("#tbcName").nth(tablerow + 1).query();

        Unit unitSelected = (Unit) tbvUnit.getSelectionModel().getSelectedItem();
        String name = unitSelected.getName();
        ObservableList<Unit> userUnits = tbvUnit.getItems();

        //Repetir un nombre
        String nameOther = null;

        for (int i = 0; i < userUnits.size(); i++) {
            if (userUnits.get(i).getSubject().getName().equalsIgnoreCase(unitSelected.getSubject().getName())) {
                nameOther = userUnits.get(i).getName();
                i = userUnits.size();
            }
        }
        clickOn(tbcNameNode);
        write(nameOther);
        push(KeyCode.ENTER);
        verifyThat("Unit name already exists in " + unitSelected.getSubject().getName() + " subject.", isVisible());
        clickOn("Aceptar");

    }

    /**
     * Test of update name INVALID method, Lenght exception, of class
     * UnitWindowController.
     */
    @Ignore
    @Test
    public void IA_testHandelUpdateNameLimitExcededException() {
        tbvUnit = lookup("#tbvUnit").query();
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        //Seleccionar el nodo de todas las filas para poder hacer click en ella.
        Integer tablerow = tbvUnit.getSelectionModel().getSelectedIndex();
        Node tbcNameNode = lookup("#tbcName").nth(tablerow + 1).query();

        Unit unitSelected = (Unit) tbvUnit.getSelectionModel().getSelectedItem();
        String name = unitSelected.getName();
        ObservableList<Unit> userUnits = tbvUnit.getItems();

        //Click pasarse del limite
        clickOn(tbcNameNode);
        write("prueba descprueba descprueba descprueba descprueba descprueba descprueba descprueba descprueba descprueba descprueba descprueba descprueba descprueba desc");
        push(KeyCode.ENTER);
        verifyThat("Unit name length must be lower than 100 characters, please change it", isVisible());
        clickOn("Aceptar");

    }

    /**
     * Test of update description method, of class UnitWindowController.
     */
    @Ignore
    @Test
    public void J_testHandelUpdateDescription() {
        tbvUnit = lookup("#tbvUnit").query();
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        //Seleccionar el nodo de todas las filas para poder hacer click en ella.
        Integer tablerow = tbvUnit.getSelectionModel().getSelectedIndex();
        Node tbcDescriptionNode = lookup("#tbcDescription").nth(tablerow + 1).query();

        Unit unitSelected = (Unit) tbvUnit.getSelectionModel().getSelectedItem();
        String description = unitSelected.getDescription();

        clickOn(tbcDescriptionNode);
        write("prueba desc");
        push(KeyCode.ENTER);
        Unit modifiedUnit = (Unit) tbvUnit.getSelectionModel().getSelectedItem();
        assertNotEquals(description, modifiedUnit.getDescription());
        clickOn(tbcDescriptionNode);

        //Devolver su valor
        write(description);
        push(KeyCode.ENTER);
    }

    /**
     * Test of update description method INVALID, of class UnitWindowController.
     */
    @Ignore
    @Test
    public void K_testHandelUpdateDescriptionLimitExcededException() {
        tbvUnit = lookup("#tbvUnit").query();
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        //Seleccionar el nodo de todas las filas para poder hacer click en ella.
        Integer tablerow = tbvUnit.getSelectionModel().getSelectedIndex();
        Node tbcDescriptionNode = lookup("#tbcDescription").nth(tablerow + 1).query();

        Unit unitSelected = (Unit) tbvUnit.getSelectionModel().getSelectedItem();
        String description = unitSelected.getDescription();

        clickOn(tbcDescriptionNode);
        write("prueba descprueba descprueba descprueba descprueba descprueba descprueba descprueba descprueba descprueba descprueba descprueba descprueba descprueba desc");
        push(KeyCode.ENTER);
        verifyThat("Unit description length must be lower than 100 characters, please change it", isVisible());
        clickOn("Aceptar");
        //Devolver su valor
        clickOn(tbcDescriptionNode);
        write(description);
        push(KeyCode.ENTER);
    }

    /**
     * Test of update hours method, of class UnitWindowController.
     */
    @Ignore
    @Test
    public void L_testHandelUpdateHours() {
        tbvUnit = lookup("#tbvUnit").query();
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        //Seleccionar el nodo de todas las filas para poder hacer click en ella.
        Integer tablerow = tbvUnit.getSelectionModel().getSelectedIndex();
        Node tbcHoursNode = lookup("#tbcHours").nth(tablerow + 1).query();

        Unit unitSelected = (Unit) tbvUnit.getSelectionModel().getSelectedItem();
        String hours = unitSelected.getHours();

        //Click en las diferentes celdas para ir modificando los datos
        clickOn(tbcHoursNode);
        write("999");
        push(KeyCode.ENTER);
        Unit modifiedUnit = (Unit) tbvUnit.getSelectionModel().getSelectedItem();

        assertNotEquals(hours, modifiedUnit.getHours());

        //Devolverle su valor
        clickOn(tbcHoursNode);
        write(hours);
        push(KeyCode.ENTER);

    }

    /**
     * Test of update hours method INVALID format, of class
     * UnitWindowController.
     */
    @Ignore
    @Test
    public void M_testHandelUpdateHoursInvalidFormat() {
        tbvUnit = lookup("#tbvUnit").query();
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        //Seleccionar el nodo de todas las filas para poder hacer click en ella.
        Integer tablerow = tbvUnit.getSelectionModel().getSelectedIndex();
        Node tbcHoursNode = lookup("#tbcHours").nth(tablerow + 1).query();

        Unit unitSelected = (Unit) tbvUnit.getSelectionModel().getSelectedItem();
        String hours = unitSelected.getHours();

        //Click en las diferentes celdas para ir modificando los datos
        clickOn(tbcHoursNode);
        write("Gato");
        push(KeyCode.ENTER);
        verifyThat("Invalid input Gato. Please enter only numbers.", isVisible());
        clickOn("Aceptar");

    }

    /**
     * Test of update dateInit method, of class UnitWindowController.
     */
    @Ignore
    @Test
    public void N_testHandelUpdateDateInit() {
        tbvUnit = lookup("#tbvUnit").query();
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        Unit unitSelected = (Unit) tbvUnit.getSelectionModel().getSelectedItem();
        Date dateInit = unitSelected.getDateInit();
        //Seleccionar el nodo de todas las filas para poder hacer click en ella.
        Integer tablerow = tbvUnit.getSelectionModel().getSelectedIndex();
        Node tbcDateInitNode = lookup("#tbcDateInit").nth(tablerow + 1).query();

        doubleClickOn(tbcDateInitNode);
        write("23/03/2023");
        press(KeyCode.ENTER);
        //Coger los valores de la asignatura modificada.
        Unit modifiedUnit = (Unit) tbvUnit.getSelectionModel().getSelectedItem();
        assertNotEquals(dateInit, modifiedUnit.getDateInit());

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dateVolver = format.format(dateInit);
        //Devolver su valor
        doubleClickOn(tbcDateInitNode);
        write(dateVolver);
        push(KeyCode.ENTER);
    }

    /**
     * Test of update dateInit method, of class UnitWindowController.
     */
    @Ignore
    @Test
    public void O_testHandelUpdateDateEnd() {
        tbvUnit = lookup("#tbvUnit").query();
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        Unit unitSelected = (Unit) tbvUnit.getSelectionModel().getSelectedItem();
        Date dateEnd = unitSelected.getDateEnd();
        //Seleccionar el nodo de todas las filas para poder hacer click en ella.
        Integer tablerow = tbvUnit.getSelectionModel().getSelectedIndex();
        Node tbcDateEndNode = lookup("#tbcDateEnd").nth(tablerow + 1).query();

        doubleClickOn(tbcDateEndNode);
        write("23/12/2024");
        press(KeyCode.ENTER);
        //Coger los valores de la asignatura modificada.
        Unit modifiedUnit = (Unit) tbvUnit.getSelectionModel().getSelectedItem();
        assertNotEquals(dateEnd, modifiedUnit.getDateEnd());

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String dateVolver = format.format(dateEnd);
        //Devolver su valor
        doubleClickOn(tbcDateEndNode);
        write(dateVolver);
        push(KeyCode.ENTER);
    }

    /**
     * Test of update subject method, of class UnitWindowController.
     */
    @Ignore
    @Test
    public void P_testHandelUpdateSubject() {
        tbvUnit = lookup("#tbvUnit").query();
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        Unit unitSelected = (Unit) tbvUnit.getSelectionModel().getSelectedItem();
        Subject subject = unitSelected.getSubject();
        Integer tablerow = tbvUnit.getSelectionModel().getSelectedIndex();
        Node tbcSubjectNode = lookup("#tbcSubject").nth(tablerow + 1).query();
        doubleClickOn(tbcSubjectNode);
        push(KeyCode.DOWN);
        push(KeyCode.DOWN);
        push(KeyCode.ENTER);

        Unit modifiedUnit = (Unit) tbvUnit.getSelectionModel().getSelectedItem();
        assertNotEquals(subject.getName(), modifiedUnit.getSubject().getName());

    }

}
