package view.exam;

import exceptions.FindErrorException;
import exceptions.UpdateErrorException;
import factories.SendFileFactory;
import interfaces.SendFileInterface;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Exam;
import models.User;

/**
 * Custom cell factory that creates a button for the Table View containing
 * exams.
 *
 * @author Alex
 */
public class ButtonEditingCell extends TableCell<Exam, String> {

    private static final Logger LOGGER = Logger.getLogger("ButtonEditingCell");

    /**
     * Interface instance to send and/or receive the files.
     */
    SendFileInterface fileInterface;

    /**
     * The button graphic for the cell.
     */
    private final Button btn;

    /**
     * The current logged user.
     */
    private final User applicationUser;

    /**
     * The stage of the window the table belongs to.
     */
    private final Stage stage;

    /**
     * The constructor using the logged user and the stage of the window.
     *
     * @param currentUser The logged user.
     * @param stage The stage of the window the table belongs to.
     */
    public ButtonEditingCell(User currentUser, Stage stage) {
        fileInterface = SendFileFactory.getModel();
        btn = new Button();
        applicationUser = currentUser;
        this.stage = stage;
    }

    /**
     * Call method for the table. It manages when to show the button graphic.
     *
     * @param param The Table Column parameter of the column the button cell
     * belongs to.
     * @return The cell with the button graphic.
     */
    public TableCell call(final TableColumn<Exam, String> param) {
        final TableCell<Exam, String> cell = new TableCell<Exam, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(btn);
                    setText(null);
                }
            }
        };
        return cell;
    }

    /**
     * Manages how and when to update the information within the cell, when and
     * how to show different text and the different actions the button carries
     * out.
     *
     * @param item The item to observe.
     * @param empty Whether it's empty or not.
     */
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
            setText(null);
        } else {
            Exam exam = getTableView().getItems().get(getIndex());
            if (exam.getDescription().equals("") || exam.getSubject().getName().equals("") || exam.getDuration().equals("") || exam.getDateInit().toString().equals("")) {
                btn.setDisable(true);
            } else {
                btn.setDisable(false);
            }
            if (exam.getFilePath() != null) {
                btn.setText("Download");
                btn.setDisable(false);
                btn.setOnAction(event -> {
                    try {
                        Exam examReceive = getTableView().getItems().get(getIndex());
                        File file = fileInterface.receiveFile(examReceive.getFilePath(), exam);
                        Desktop.getDesktop().open(file);
                    } catch (IOException | IllegalArgumentException | FindErrorException ex) {
                        LOGGER.log(Level.SEVERE, "File Not Found.");
                        new Alert(Alert.AlertType.WARNING, "File Not Found", ButtonType.OK).showAndWait();
                    }
                });
            } else {
                if (applicationUser.getUser_type().equals("Teacher")) {
                    btn.setText("Add file");
                    btn.setOnAction(event -> {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Choose a file.");
                        File file = fileChooser.showOpenDialog(stage);
                        if (file != null) {
                            try {
                                fileInterface.sendFile(file, getTableView().getItems().get(getIndex()));
                            } catch (UpdateErrorException ex) {
                                LOGGER.log(Level.SEVERE, "Error updating info.");
                                new Alert(Alert.AlertType.WARNING, "Error updating info.", ButtonType.OK).showAndWait();
                            }
                        }
                    });
                } else if (applicationUser.getUser_type().equals("Student")) {
                    btn.setText("Not available");
                    btn.setDisable(true);
                }
            }
            setGraphic(btn);
            setText(null);
        }
    }
}
