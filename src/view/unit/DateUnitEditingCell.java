package view.unit;

import java.text.DateFormat;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.util.StringConverter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import models.Unit;

/**
 * TableCell implementation for editing a Date with a DatePicker. Displays the
 * date in text form when not in editing mode.
 */
public class DateUnitEditingCell extends TableCell<Unit, Date> {

    private DatePicker dpUnitCell;
    private Locale locale;
    private DateFormat dateFormatter;

    public DateUnitEditingCell() {
        this.dpUnitCell = new DatePicker();
        // Configurar un StringConverter para convertir entre Date y String
        dpUnitCell.setConverter(new StringConverter<LocalDate>() {

            @Override
            public String toString(LocalDate object) {
                if (object != null) {
                    return dateFormatter.format(Date.from(object.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string);
                } else {
                    return null;
                }
            }
        });

    }

    @Override
    protected void updateItem(Date date, boolean empty) {
        super.updateItem(date, empty);
        locale = Locale.getDefault();
        dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {

            if (isEditing()) {
                setText(null);
                setGraphic(dpUnitCell);
            } else if (date != null) {
                String dateText = dateFormatter.format(date);
                setText(dateText);
                setGraphic(null);
            }
        }
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            dpUnitCell = new DatePicker();
            dpUnitCell.setOnAction((e) -> {
                commitEdit(Date.from(dpUnitCell.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            });
            setText(null);
            setGraphic(dpUnitCell);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setGraphic(null);
    }

}
