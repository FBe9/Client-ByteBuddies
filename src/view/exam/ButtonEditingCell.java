/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.exam;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    public ButtonEditingCell(User currentUser) {
        btn = new Button();
        applicationUser = currentUser;
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
            //exam.setFilePath("");
            if (!exam.getFilePath().equals("")) {
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
                        // CALL SENDFILE INTERFACE
                        // SEND METHOD
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
