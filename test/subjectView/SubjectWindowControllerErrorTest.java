package subjectView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import main.SubjectMain;
import models.Subject;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 * Class for teasting logic error of subject window.
 *
 * @author irati
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SubjectWindowControllerErrorTest extends ApplicationTest {

    /**
     * TableView used in the user interface to display subjects.
     */
    private TableView<Subject> tableView;

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
     * Method to see the errror if the server is not running.
     */
    //@Ignore
    //@Test
    public void test0_serverConectionError() {
        verifyThat("Unable to connect to the server. Please check the server availability and try again later.", isVisible());

    }

    /**
     * Method to check the error when written a subject with an existing name
     */
    //@Ignore
    @Test
    public void test1_SubjectWithSameName() {
        tableView = lookup("#tbSubjects").query();
        //Coge de la lista de asignaturas visibles un nombre que exista para escribirlo.
        List<Subject> dataSubject = new ArrayList<>(tableView.getItems());
        String name = dataSubject.get(0).getName();
        //Hace click en el primer nodo
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        //Coge la posición y le suma uno (Empieza desde 0)
        Integer tablerow = tableView.getSelectionModel().getSelectedIndex();
        Node tableColumnName = lookup("#tbColNameSub").nth(tablerow + 1).query();
        clickOn(tableColumnName);
        //Escribe el nombre
        write(name);
        push(KeyCode.ENTER);
        //Verifica que se muestre la alerta.
        verifyThat("Subject name already exists.", isVisible());
        press(KeyCode.ENTER);

    }

    /**
     * Method to check the error during inserting a date init after the date end
     */
    //@Ignore
    @Test
    public void test2_dateInitAfterDateEnd() {
        tableView = lookup("#tbSubjects").query();
        //Parseo de la fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //Selecciona una fila
        Node row = lookup(".table-row-cell").nth(1).query();

        clickOn(row);
        //Coge el número de la posición de la fila seleccionada
        Integer tablerow = tableView.getSelectionModel().getSelectedIndex();
        //Coge el objecto subject de la fila seleccionada.
        Subject subject = (Subject) tableView.getSelectionModel().getSelectedItem();
        // Coge el valor del date end actual para la fila seleccionada
        LocalDate localDateEnd = subject.getDateEnd().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        // Sumar un día a la fecha
        LocalDate newDate = localDateEnd.plusDays(1);
        String date = newDate.format(formatter);
        Node tableColumnInitDate = lookup("#tbColInitDateSub").nth(tablerow + 1).query();
        //Hace click en el datepicker y escribe el valor
        doubleClickOn(tableColumnInitDate);
        clickOn(tableColumnInitDate);
        write(date);
        push(KeyCode.ENTER);
        //Verifica que se muestre el mensaje de error.
        verifyThat("The start date must be before the end date of the subject.", isVisible());
        push(KeyCode.ENTER);

    }

    /**
     * Method to check the error during inserting a end date before init date
     */
    //@Ignore
    @Test
    public void test3_dateEndBeforeDateInit() {
        tableView = lookup("#tbSubjects").query();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //Selecciona una fila
        Node row = lookup(".table-row-cell").nth(1).query();
        clickOn(row);

        Integer tablerow = tableView.getSelectionModel().getSelectedIndex();
        Subject subject = (Subject) tableView.getSelectionModel().getSelectedItem();
        // Coge el valor del init date actual para la fila seleccionada
        LocalDate localDateInit = subject.getDateInit().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        // Le quita un día
        LocalDate newDate = localDateInit.minusDays(1);
        String date = newDate.format(formatter);
        Node tableColumnEndDate = lookup("#tbColEndDateSub").nth(tablerow + 1).query();
        //Hace click en el datepicker y escribe el valor
        doubleClickOn(tableColumnEndDate);
        clickOn(tableColumnEndDate);
        write(date);
        push(KeyCode.ENTER);
        //Verifica que se muestre el mensaje de error
        verifyThat("The end date must be later than the start date of the subject.", isVisible());
        push(KeyCode.ENTER);

    }

    /**
     * Method to check the error if a date is not between the range dates
     */
    //@Ignore
    @Test
    public void test4_dateOutOfRange() {
        //Coge los valores entre los que tiene que estar la fecha
        String startDateConfig = ResourceBundle.getBundle("config.config").getString("STARTDATE");
        String endDateConfig = ResourceBundle.getBundle("config.config").getString("ENDDATE");

        LocalDate startDate = LocalDate.parse(startDateConfig);
        LocalDate endDate = LocalDate.parse(endDateConfig);

        tableView = lookup("#tbSubjects").query();
        Node row = lookup(".table-row-cell").nth(1).query();

        clickOn(row);

        Integer tablerow = tableView.getSelectionModel().getSelectedIndex();
        //Inserta una fecha fuera del rango 
        Node tableColumnInitDate = lookup("#tbColInitDateSub").nth(tablerow + 1).query();
        if (tableColumnInitDate != null) {
            //Click en la columna
            clickOn(tableColumnInitDate);
            clickOn(tableColumnInitDate);
            //Escribe en la columna
            write("01/01/2012");
            push(KeyCode.ENTER);
            //Verifica el mensaje de error
            verifyThat("Our application only stores information from year " + startDate.getYear() + " to year " + endDate.getYear() + ".", isVisible());
            push(KeyCode.ENTER);
        }
        //Inserta una fecha fuera del rango 
        Node tableColumnEndDate = lookup("#tbColEndDateSub").nth(tablerow + 1).query();
        if (tableColumnEndDate != null) {
             //Click en la columna
            clickOn(tableColumnEndDate);
            clickOn(tableColumnEndDate);
            //Escribe en la columna
            write("02/01/2028");
            push(KeyCode.ENTER);
            //Verifica el mensaje de error
            verifyThat("Our application only stores information from year " + startDate.getYear() + " to year " + endDate.getYear() + ".", isVisible());
            push(KeyCode.ENTER);
        }

    }

    /**
     * Checks if there is an error when attempting to create a new subject
     * without completing the creation of another one.
     */
    //@Ignore
    @Test
    public void test5_TwoSubjectsWithEmptyName() {
        tableView = lookup("#tbSubjects").query();
        List<Subject> dataSubject = new ArrayList<>(tableView.getItems());
        Boolean empty = false;
        //Mira si hay otra asignatura que tenga el nombre a nulo
        for (Subject subject : dataSubject) {
            if (subject.getName() == null) {
                empty = true;
            }
        }
        //Si ya existe una asignatura en nulo se da click solo una vez (Para que no salgan dos alertas)
        if (empty) {
            clickOn("#btnCreateSubject");
        } else {
            //Si no hay ninguna asignatura con nombre en nulo, se le da dos veces para provocar el error.
            clickOn("#btnCreateSubject");
            clickOn("#btnCreateSubject");
        }
        //Se verifica que se muestre el mensaje de error.
        verifyThat("It looks like you've started creating a subject, but you forgot to give it a name. Please complete that one before creating another.", isVisible());
        push(KeyCode.ENTER);
    }

    /**
     * Method to see the error if you try to insert a teacher in a subject that
     * doesn't have name.
     */
    //@Ignore
    @Test
    public void test6_insertATeacherInANullSubjectName() {
        tableView = lookup("#tbSubjects").query();
        List<Subject> dataSubject = new ArrayList<>(tableView.getItems());
        Boolean empty = false;
        Integer position = null;
        //Se busca una asignatura que no tenga nombre.
        for (Subject subject : dataSubject) {
            if (subject.getName() == null) {
                empty = true;
                position = position = dataSubject.indexOf(subject);
            }
        }
        //Si la hay se da click en el listview
        Node tableColumTeachers = null;
        if (empty) {
            tableColumTeachers = lookup("#tbColTeachersSub").nth(position + 1).query();

        } else {
            clickOn("#btnCreateSubject");
            int rowCount = tableView.getItems().size();
            tableColumTeachers = lookup("#tbColTeachersSub").nth(rowCount).query();
        }
        doubleClickOn(tableColumTeachers);
        push(KeyCode.DOWN);
        push(KeyCode.ENTER);
        //Se verifica que salga el error
        verifyThat("To assign a teacher to a subject, the subject must have a name.", isVisible());
        push(KeyCode.ENTER);

    }

    /**
     * Method to check the error if trying to see the units for a subjects that has
     * a null name
     */
    //@Ignore
    @Test
    public void test7_tryToSeeUnitOfASubjectWithNullName() {
        tableView = lookup("#tbSubjects").query();
        List<Subject> dataSubject = new ArrayList<>(tableView.getItems());
        Boolean empty = false;
        Integer position = null;
        //Verifica que exista una asignatura con el nombre a null
        for (Subject subject : dataSubject) {
            if (subject.getName() == null) {
                empty = true;
                position = dataSubject.indexOf(subject);
            }
        }
        Node tableColumUnit = null;
        //Si existe busca su posición
        if (empty) {
            tableColumUnit = lookup("#tbColUnits").nth(position + 1).query();

        } else {
            //Si no existe, crea una asignatura.
            clickOn("#btnCreateSubject");
            int rowCount = tableView.getItems().size();
            tableColumUnit = lookup("#tbColUnits").nth(rowCount).query();
        }

        clickOn(tableColumUnit);
        //Verifica que se muestre el error
        verifyThat("Please insert a subject name before showing units", isVisible());
        push(KeyCode.ENTER);

    }
    /**
     * Method to check the error if trying to see the exams for a subjects that has
     * a null name.
     */
    //@Ignore
    @Test
    public void test8_tryToSeeExamOfASubjectWithNullName() {
        tableView = lookup("#tbSubjects").query();
        List<Subject> dataSubject = new ArrayList<>(tableView.getItems());
        Boolean empty = false;
        Integer position = null;
        //Verifica que exista una asignatura con el nombre a null
        for (Subject subject : dataSubject) {
            if (subject.getName() == null) {
                empty = true;
                position = dataSubject.indexOf(subject);
            }
        }
        Node tableColumExam = null;
        //Si existe busca su posición
        if (empty) {
            tableColumExam = lookup("#tbColExams").nth(position + 1).query();
            clickOn(tableColumExam);

        } else {
            //Si no existe, crea una asignatura.
            clickOn("#btnCreateSubject");
            int rowCount = tableView.getItems().size();
            tableColumExam = lookup("#tbColExams").nth(rowCount).query();
            clickOn(tableColumExam);
        }
        //Verifica que se muestre el error
        verifyThat("Please insert a subject name before showing exams", isVisible());
        push(KeyCode.ENTER);

    }
    /**
     * Method to check the colum name has only letters.
     */
    //@Ignore
    @Test
    public void test9_columnNameOnlyLetters() {
        tableView = lookup("#tbSubjects").query();

        Node row = lookup(".table-row-cell").nth(1).query();

        clickOn(row);

        Integer tablerow = tableView.getSelectionModel().getSelectedIndex();
        Node tableColumnName = lookup("#tbColNameSub").nth(tablerow + 1).query();
        clickOn(tableColumnName);
        //Inserta números en una columna de solo texto
        String number = "11111";
        write(number);
        push(KeyCode.ENTER);
        //Verifica que se muestre el error.
        verifyThat("Invalid input " + number + ". Please enter only letters.", isVisible());
        push(KeyCode.ENTER);

    }
    /**
     * Method to check the column hours only allows numbers
     */
    //@Ignore
    @Test
    public void testA10_columnHoursOnlyNumbers() {
        tableView = lookup("#tbSubjects").query();
        Node row = lookup(".table-row-cell").nth(1).query();

        clickOn(row);

        Integer tablerow = tableView.getSelectionModel().getSelectedIndex();
        Node tableColumnHours = lookup("#tbColHoursSub").nth(tablerow + 1).query();
        clickOn(tableColumnHours);
         //Inserta letras en una columna de solo números.
        String letters = "AAAA";
        write(letters);
        push(KeyCode.ENTER);
        //Verifica que se muestre el error
        verifyThat("Invalid input " + letters + ". Please enter only numbers.", isVisible());
        push(KeyCode.ENTER);

    }

}
