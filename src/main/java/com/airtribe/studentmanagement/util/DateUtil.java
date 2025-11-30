package main.java.com.airtribe.studentmanagement.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
public final class DateUtil {

    private DateUtil() {
    }

    public static LocalDate parseDateOrNull(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(raw.trim());
        } catch (DateTimeParseException ex) {
            return null;
        }
    }
}
