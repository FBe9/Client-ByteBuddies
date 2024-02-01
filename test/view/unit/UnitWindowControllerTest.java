package view.unit;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.UnitMain;
import models.LanguageType;
import models.LevelType;
import models.Subject;
import models.Unit;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

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
    //@Ignore
    @Test
    public void A_testHandelCreateButtonAction() {
        tbvUnit = lookup("#tbvUnit").query();
        clickOn("#btnCreateUnit");
        Integer count = tbvUnit.getItems().size();
        ObservableList<Unit> userUnits = tbvUnit.getItems();

        //Hay una fila de más en la tabla despues de usar el metodo.
        //assertEquals(tbvUnit.getItems().size(), count + 1);
        Integer unitRow = 0;
        Unit newUnit = new Unit();
        for (int i = 0; i < userUnits.size(); i++) {
            if (userUnits.get(i).getName().equalsIgnoreCase("")) {
                if (userUnits.get(i).getSubject().getName().equalsIgnoreCase("PGR")) {
                    newUnit = userUnits.get(i);
                    unitRow = tbvUnit.getItems().indexOf(newUnit);
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
        assertEquals(newUnit.getName(), "");
        assertEquals(newUnit.getSubject(), "PGR");
        assertEquals(newUnit.getDescription(), "");
        assertEquals(dateInitLocal, LocalDate.now());
        assertEquals(dateEndLocal, LocalDate.now());
        assertEquals(newUnit.getHours(), "0");

        //Borrar prueba en la trabla
        Node row = lookup(".table-row-cell").nth(unitRow).query();
        clickOn(row);
        clickOn("#btnDeleteUnit");
    }

    /**
     * Test of handelSearchButtonAction method, of class UnitWindowController.
     */
    @Ignore
    @Test
    public void testHandelSearchButtonAction() {
    }

    /**
     * Test of handelDeleteButtonAction method, of class UnitWindowController.
     */
    @Ignore
    @Test
    public void testHandelDeleteButtonAction() {
    }

}
