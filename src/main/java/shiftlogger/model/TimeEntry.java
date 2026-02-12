package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class TimeEntry {
    private final LocalDate date;
    private final LocalTime start;
    private final LocalTime end;
    private final UUID id;

    
    public TimeEntry(UUID id, LocalDate date, LocalTime start, LocalTime end){
        this. id = id;
        this.date = date;
        this.start = start;
        this.end = end;
    }

    public TimeEntry(LocalDate date, LocalTime start, LocalTime end) {
        this.id = UUID.randomUUID();
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

    public UUID getID(){
        return id;
    }
}
