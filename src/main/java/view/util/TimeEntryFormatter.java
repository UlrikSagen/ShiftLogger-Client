package view.util;

import model.TimeEntry;

public class TimeEntryFormatter {
    public static String entryToString(TimeEntry entry){
        return entry.getDate() + ":   " + entry.getStart() + "  -   " + entry.getEnd();
    }
}
