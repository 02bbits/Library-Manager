package util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

public class DateUtil {
    public static boolean isDateBefore(String firstDate, String secondDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date1 = LocalDate.parse(firstDate, formatter);
            LocalDate date2 = LocalDate.parse(secondDate, formatter);
            return date1.isBefore(date2);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Dates must follow YYYY-MM-DD.");
        }
    }

    public static ArrayList<LocalDate> getDatesOfWeek(int weekNumber) {
        ArrayList<LocalDate> weekDates = new ArrayList<>();

        LocalDate firstDayOfYear = LocalDate.of(LocalDate.now().getYear(), 1, 1);

        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);

        LocalDate startOfWeek = firstDayOfYear.with(weekFields.weekOfWeekBasedYear(), weekNumber).with(weekFields.dayOfWeek(), 1);

        for (int i = 0; i < 7; i++) {
            weekDates.add(startOfWeek.plusDays(i));
        }

        return weekDates;
    }

    public static int getCurrentWeek() {
        LocalDate date = LocalDate.now();
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);
        return date.get(weekFields.weekOfWeekBasedYear());
    }
}
