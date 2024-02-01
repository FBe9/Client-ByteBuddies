package view.exam;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import models.Exam;

/**
 * Custom cell factory for the Table View containing Exams.
 *
 * @author Alex
 */
public class CustomEditingCell extends TableCell<Exam, String> {

    /**
     * The text field to show in editing mode.
     */
    private TextField textField;

    /**
     * Default empty constructor of the class.
     */
    public CustomEditingCell() {
    }

    /**
     * Manages the beginning of the edit event of the cell.
     */
    @Override
    public void startEdit() {
        super.startEdit();
        textField = new TextField("");
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

    /**
     * Manages the end of the edit event of the cell.
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText((String) getItem());
        setGraphic(null);
    }

    /**
     * Manages when and how to update the information within the cell, whether
     * to show the text field or the plain text.
     *
     * @param item The item to observe.
     * @param empty Whether it's empty or not.
     */
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

    /**
     * Default string representation of the item within the cell.
     *
     * @return The string representation of the item.
     */
    private String getString() {
        return getItem() == null ? "" : getItem();
    }
}
