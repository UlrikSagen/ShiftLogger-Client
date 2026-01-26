package view.util;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalTime;

import model.TimeEntry;

public class timeEntryFormatter {

    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm");

    public static String entryToString(TimeEntry entry){
        return entry.getDate() + ":   " + entry.getStart() + "  -   " + entry.getEnd();
    }

    public static String hoursWorked(int totalMinutes){
        return(totalMinutes/60 + " hours and " + totalMinutes % 60 + " minutes.");
    }
    public static String getDate(LocalDate date){
        return date.format(DATE);
    }
    public static String getTime(LocalTime time){
        return time.format(TIME);
    }
}
