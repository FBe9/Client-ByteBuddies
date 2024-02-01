package view.subject;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.stage.Stage;
import models.Subject;
import models.User;
import view.exam.ExamWindowController;

/**
 * Cell factory for hyperlink Exam
 *
 * @author irati
 */
public class HyperLinkEditingCellExam extends TableCell<Subject, String> {

    /**
     * Hyperlink used for navigation or actions in the UI.
     */
    private Hyperlink link;

    /**
     * The primary stage for the JavaFX application.
     */
    private Stage stage;

    /**
     * User object representing the current user of the application.
     */
    private User user;

    /**
     * Default constructor for DateSubjectEditingCell.
     */
    public HyperLinkEditingCellExam() {
    }

    /**
     * Constructor for HyperLinkEditingCellExam.
     *
     * @param user The user for whom exams are displayed.
     * @param primaryStage The primary stage to display the exam window.
     */
    public HyperLinkEditingCellExam(User user, Stage primaryStage) {
        link = new Hyperlink("Show exams");
        this.stage = primaryStage;
        this.user = user;

        //Se establece que cuando se pulsa en el hiperlink se habra la ventana de examenes.
        link.setOnAction(evt -> {
            try {
                Subject currentSubject = getTableView().getItems().get(getIndex());
                if (currentSubject.getName() != null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/exam/ExamWindow.fxml"));
                    Parent root = (Parent) loader.load();
                    // Se abre la ventana de exam
                    ExamWindowController controller = (ExamWindowController) loader.getController();
                    controller.setUser(user);
                    controller.setStage(stage);
                    controller.initStage(root);
                    controller.setCurrentSubject(currentSubject);
                } else {
                    showErrorAlert("Please insert a subject name before showing exams");
                }

            } catch (IOException ex) {
                Logger.getLogger(HyperLinkEditingCellExam.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * Updates the graphic content of the cell based on the provided item.
     * Displays the hyperlink to show exams.
     */
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(link);
        }
    }

    /**
     * Shows an alert containing the exception message.
     * 
     * @param errorMsg The message to show.
     */
    public void showErrorAlert(String errorMsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
        alert.showAndWait();

    }
}
