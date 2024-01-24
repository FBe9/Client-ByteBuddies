package view.subject;

import java.text.DateFormat;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.util.StringConverter;
import models.Subject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

/**
 * TableCell implementation for editing a Date with a DatePicker. Displays the
 * date in text form when not in editing mode.
 */
public class DateSubjectEditingCell extends TableCell<Subject, Date> {

    private DatePicker dpSubjectCell;
    private DateFormat dateFormatter;

    /**
     * Default constructor for DateSubjectEditingCell.
     */
    public DateSubjectEditingCell() {
    }

    /**
     * Updates the graphic content of the cell based on the provided date.
     * Displays the date in text form when not in editing mode.
     */
    @Override
    protected void updateItem(Date date, boolean empty) {
        super.updateItem(date, empty);
        //La columna de la fecha de inicio (tbColStartDate) y fecha de finalización ((tbColEndDate) de la tabla de subject se mostrará en un formato que se ajuste a la configuración local del ordenador del usuario.
        Locale locale = Locale.getDefault();
        dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {

            if (isEditing()) {
                setText(null);
                setGraphic(dpSubjectCell);
            } else if (date != null) {
                String dateText = dateFormatter.format(date);
                setText(dateText);
                setGraphic(null);
            }
        }
    }

    /**
     * Initiates the editing process for the cell. Creates a DatePicker for date
     * selection.
     */
    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            dpSubjectCell = new DatePicker();
            dpSubjectCell.setOnAction((e) -> {
                commitEdit(Date.from(dpSubjectCell.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            });
            setText(null);
            setGraphic(dpSubjectCell);
        }
    }

    /**
     * Cancels the editing process and clears the graphic content.
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setGraphic(null);
    }

}
