package view.exam;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import models.Exam;

/**
 *
 * @author Alex
 */
public class CustomEditingCell extends TableCell<Exam, String> {

    private TextField textField;

    public CustomEditingCell() {
    }

    @Override
    public void startEdit() {
        super.startEdit();
        textField = new TextField(" ");
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0,
                    Boolean arg1, Boolean arg2) {
                if (!arg2) {
                    commitEdit(textField.getText());
                }
            }
        });
        if (!isEmpty()) {
            textField.setText(getString());
        }
        setText(null);
        setGraphic(textField);
        textField.selectAll();
        
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText((String) getItem());
        setGraphic(null);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}
