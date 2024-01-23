package view.unit;

import javafx.event.Event;
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
        httpUnitCell.setText("View all Exercises");
    }

    public void handleHyperlinkActionEvent(Event event) {
    
    }
}