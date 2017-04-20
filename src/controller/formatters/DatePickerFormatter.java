package controller.formatters;

import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Matus Cuper on 17.4.2017.
 *
 * Formatter for {@link javafx.scene.control.DatePicker}
 * Date format in database and whole application is ISO 8601,
 * DatePicker default is MM/dd/yyyy which is completely unreadable
 */
public class DatePickerFormatter extends StringConverter<LocalDate> {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Override
    public String toString(LocalDate localDate) {
        return localDate != null ? dateTimeFormatter.format(localDate) : "";
    }

    @Override
    public LocalDate fromString(String s) {
        if ((s == null) || (s.trim().isEmpty()))
            return null;
        return LocalDate.parse(s, dateTimeFormatter);
    }
}
