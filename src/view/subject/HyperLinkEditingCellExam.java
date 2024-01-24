package view.subject;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

    private Hyperlink link;
    private Stage stage;
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
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/exam/ExamWindow.fxml"));
                Parent root = (Parent) loader.load();
                // Obtain the Sign In window controller
                ExamWindowController controller = (ExamWindowController) loader.getController();
                controller.setStage(stage);
                controller.initStage(root);
                controller.setUser(user);
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
}
