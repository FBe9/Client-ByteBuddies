package view.exam;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;
import models.Exam;

/**
 * Custom date cell factory for the Table View containing exams. Creates a date
 * picker.
 *
 * @author Alex
 */
public class DateExamEditingCell extends TableCell<Exam, Date> {

    /**
     * The date picker graphic.
     */
    private DatePicker datePicker;

    /**
     * The date format converter.
     */
    private LocalDateStringConverter converter;

    /**
     * The OS date format locale.
     */
    private Locale locale;

    /**
     * The date formatter.
     */
    private DateFormat dateFormatter;

    /**
     * The date in string format.
     */
    private String dateText;

    /**
     * Default constructor that manages the different variable formats and
     * information transaction between them.
     */
    public DateExamEditingCell() {
        this.datePicker = new DatePicker();

        // Configurar un StringConverter para convertir entre Date y String
        datePicker.setConverter(new StringConverter<LocalDate>() {
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

    /**
     * Manages when and how to update the information of the cell.
     *
     * @param date The item to observe.
     * @param empty Whether it's empty or not.
     */
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
                setText(getTableView().getItems().get(getIndex()).getDateInit().toString());
                setGraphic(datePicker);
            } else if (date != null) {
                dateText = dateFormatter.format(date);
                setText(dateText);
                setGraphic(null);
            }  else{
                if (!getTableView().getItems().get(getIndex()).getDescription().equals("")) {
                    try{
                        setText(dateFormatter.format(getTableView().getItems().get(getIndex()).getDateInit()));
                    } catch(NullPointerException ex){
                        setText("");
                    }
                } else{
                    setText("");
                }
                setGraphic(null);
            }
        }
    }

    /**
     * Manages the beginning of the edit event.
     */
    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            datePicker = new DatePicker();
            datePicker.focusedProperty().addListener((ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) -> {
                if (!arg2) {
                    commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                }
            });
            datePicker.setOnAction((e) -> {
                commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            });
            setText(null);
            setGraphic(datePicker);
        }
    }

    /**
     * Manages the end of the editing event.
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setGraphic(null);
        setText(dateText);
    }
}
