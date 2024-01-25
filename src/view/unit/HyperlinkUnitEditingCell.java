package view.unit;

import exceptions.ExerciseErrorException;
import exceptions.FindErrorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableCell;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableRow;
import javafx.stage.Stage;
import models.Unit;
import models.User;
import view.exercise.ExerciseController;

/**
 * TableCell implementation for Hyperlink for Unit table to open exercises
 * window.
 *
 * @author Nerea
 */
public class HyperlinkUnitEditingCell extends TableCell<Unit, String> {

    private User loggedUser;
    private Hyperlink httpUnitCell;
    private Stage stage;

    /**
     * Default constructor for HyperlinkUnitEditingCell.
     */
    public HyperlinkUnitEditingCell() {
    }

    /**
     * Constructor for HyperlinkUnitEditingCell.
     *
     * @param loggedUser The user that is connected to the application.
     * @param stage The stage that the window used.
     */
    public HyperlinkUnitEditingCell(User loggedUser, Stage stage) {
        // Configurar el Hyperlink
        httpUnitCell = new Hyperlink();
        httpUnitCell.setText("View all Exercises");
        httpUnitCell.setOnAction(evt -> {
            /**
             * Se abrirá la ventana de Exercises pasándole como parámetro el
             * objeto de tipo Unit seleccionado mediante el hyperlink que tiene
             * esta columna.
             */
            try {
                Integer index = getTableRow().getIndex();
                Unit unit = getTableView().getItems().get(index);
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/exercise/exercise.fxml"));
                Parent root = (Parent) loader.load();
                // Obtain the Sign In window controller
                ExerciseController controller = (ExerciseController) loader.getController();
                controller.setStage(stage);
                controller.initialize(root, loggedUser);
                controller.setCurrentUnit(unit);
            } catch (IOException | ExerciseErrorException | FindErrorException ex) {
                Logger.getLogger(HyperlinkUnitEditingCell.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * Updates the graphic content of the cell based on the provided item.
     * Displays the hyperlink to show units.
     *
     * @param item
     * @param empty
     */
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(httpUnitCell);
        }
    }
}
