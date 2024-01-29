/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.exam;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Exam;
import models.Student;
import models.Teacher;
import models.User;

/**
 *
 * @author Alex
 */
public class ButtonEditingCell extends TableCell<Exam, String> {

    private final Button btn;
    private final User applicationUser;
    private final Stage stage;

    public ButtonEditingCell(User currentUser, Stage stage) {
        btn = new Button();
        applicationUser = currentUser;
        this.stage = stage;
    }

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
                    // CALL SENDFILE INTERFACE
                    // RECEIVE METHOD
                });
            } else {
                if (applicationUser instanceof Teacher) {
                    btn.setText("Add file");
                    btn.setOnAction(event -> {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Choose a file.");
                        File file = fileChooser.showOpenDialog(stage);
                        if (file != null) {
                            try {
                                Desktop.getDesktop().open(file);
                                // CALL SENDFILE INTERFACE
                                // SEND METHOD
                            } catch (IOException ex) {
                                Logger.getLogger(ButtonEditingCell.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    });

                } else if (applicationUser instanceof Student) {
                    btn.setText("Not available");
                    btn.setDisable(true);
                }
            }
            setGraphic(btn);
            setText(null);
        }
    }
}
