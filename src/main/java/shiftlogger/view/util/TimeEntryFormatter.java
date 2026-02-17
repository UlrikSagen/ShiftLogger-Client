package shiftlogger.view.util;

import java.time.format.DateTimeFormatter;

import shiftlogger.model.TimeEntry;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class TimeEntryFormatter {

    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm");

    public static String entryToString(TimeEntry entry){
        return entry.date() + ":   " + entry.start() + "  -   " + entry.end();
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
    public static String getSalary(float salary){
        return String.format("%1$,3.2f", salary);
    }
    public static String getOvertime(Duration overtime){
        return String.valueOf("OT:  " + overtime.toHours()%60) + " hours and "+ String.valueOf(overtime.toMinutes()/60 + " minutes.");
    }
}
