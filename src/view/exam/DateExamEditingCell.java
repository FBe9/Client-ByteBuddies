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
 *
 * @author Alex
 */
public class DateExamEditingCell extends TableCell<Exam, Date> {

    private DatePicker datePicker;
    private LocalDateStringConverter converter;

    private Locale locale;
    private DateFormat dateFormatter;
    private String dateText;

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

    @Override
    protected void updateItem(Date date, boolean empty) {
        super.updateItem(date, empty);
        Locale locale = Locale.getDefault();
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
            } else {
                if (!getTableView().getItems().get(getIndex()).getDescription().equals("")) {
                    setText(dateFormatter.format(getTableView().getItems().get(getIndex()).getDateInit()));
                }
                setGraphic(null);
            }
        }
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            datePicker = new DatePicker();
            datePicker.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> arg0,
                        Boolean arg1, Boolean arg2) {
                    if (!arg2) {
                        commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    }
                }
            });
            datePicker.setOnAction((e) -> {
                commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            });
            setText(null);
            setGraphic(datePicker);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setGraphic(null);
        setText(dateText);
    }

}
