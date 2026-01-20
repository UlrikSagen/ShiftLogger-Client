package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeEntry {
    private final LocalDate date;
    private final LocalTime start;
    private final LocalTime end;

    
    public TimeEntry(LocalDate date, LocalTime start, LocalTime end) {
        this.date = date;
        this.start = start;
        this.end = end; 
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }
}
