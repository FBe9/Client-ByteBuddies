package view.subject;

import exceptions.FindErrorException;
import factories.SubjectFactory;
import factories.UnitFactory;
import interfaces.SubjectManager;
import interfaces.UnitInterface;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.stage.Stage;
import models.Subject;
import models.Unit;
import models.User;
import view.unit.UnitWindowController;

/**
 * Cell factory for hyperlink Unit
 *
 * @author irati
 */
public class HyperLinkEditingCellUnit extends TableCell<Subject, String> {

    private Hyperlink link;
    private Stage stage;
    private User user;
    private ObservableList<Unit> units;
    private ObservableList<Subject> subjects;

    /**
     * Default constructor for DateSubjectEditingCell.
     */
    public HyperLinkEditingCellUnit() {

    }

    /**
     * Constructor for HyperLinkEditingCellExam.
     *
     * @param user The user for whom exams are displayed.
     * @param primaryStage The primary stage to display the exam window.
     */
    public HyperLinkEditingCellUnit(User user, Stage primaryStage) {
        link = new Hyperlink("Show units");
        this.stage = primaryStage;
        this.user = user;

        link.setOnAction(evt -> {
            try {
                Subject currentSubject = getTableView().getItems().get(getIndex());

                if (currentSubject.getName() != null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/unit/UnitWindow.fxml"));
                    Parent root = (Parent) loader.load();
                    UnitWindowController controller = (UnitWindowController) loader.getController();
                    controller.setStage(stage);
                    controller.initStage(root, user);
                    controller.setCurrentSubject(currentSubject);
                } else {
                    showErrorAlert("Please insert a name before showing units");
                }

            } catch (IOException ex) {
                Logger.getLogger(HyperLinkEditingCellUnit.class.getName()).log(Level.SEVERE, null, ex);
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

    public void showErrorAlert(String errorMsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, errorMsg, ButtonType.OK);
        alert.showAndWait();

    }

}
