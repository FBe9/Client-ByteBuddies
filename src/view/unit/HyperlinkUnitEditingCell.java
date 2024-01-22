package view.unit;

import javafx.scene.control.TableCell;
import javafx.scene.control.Hyperlink;

import models.Unit;

/**
 * TableCell implementation for editing a Date with a DatePicker. Displays the
 * date in text form when not in editing mode.
 */
public class HyperlinkUnitEditingCell extends TableCell<Unit, Hyperlink> {

    private Hyperlink httpUnitCell;

    public HyperlinkUnitEditingCell() {
        // Configurar el Hyperlink
        httpUnitCell = new Hyperlink();
    }

    @Override
    protected void updateItem(Hyperlink http, boolean empty) {
        super.updateItem(http, empty);
       
        if (empty) {
            setText(null);
        } else {

            if (isEditing()) {
                
            } else if (http != null) {
                
            }
        }
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            httpUnitCell = new Hyperlink();
            httpUnitCell.setOnAction((e) -> {
                //commitEdit(Hyperlink.);
            });
            setText(null);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setGraphic(null);
    }

}