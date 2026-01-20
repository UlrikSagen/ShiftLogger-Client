package service;

import model.*;
import service.TimeCalculator;

import java.time.*;
import java.util.List;
import java.util.ArrayList;


public class TimeService {
    
    public List<TimeEntry> addOrEdit(List<TimeEntry> entries, LocalDate date, LocalTime start, LocalTime end){

        List<TimeEntry> out = new ArrayList<>();
        boolean replaced = false;

        for (TimeEntry entry : entries) {
            if (entry.getDate().equals(date)){
                out.add(new TimeEntry(date, start, end));
                replaced = true;
            } else {
                out.add(entry);
            }
        }

        if (!replaced) {
            out.add(new TimeEntry(date, start, end));
        }
        return out;
    }

    public List<TimeEntry> deleteEntry(List<TimeEntry> entries, LocalDate date){
        List<TimeEntry> out = new ArrayList<>();

        for (TimeEntry entry : entries) {
            if (!entry.getDate().equals(date)){
                out.add(entry);
            }
        }
        return out;
    }

    public int getTotalMinutes(List<TimeEntry> entries){
        int totalMinutes = 0;
        return TimeCalculator.getTotalMinutes(entries);
    }

    public List<TimeEntry> filterByMonth(List<TimeEntry> entries, int month, int year){
        List<TimeEntry> filtered = new ArrayList<>();

        for (TimeEntry entry : entries) {
            if (entry.getDate().getMonthValue() == month && entry.getDate().getYear() == year) {
                filtered.add(entry);
            }
        }

        return filtered;
    }

    public List<String> getListOfStrings(List<TimeEntry> entries){
        List<String> listOfStrings =  new ArrayList<>();
        String entryToString;
        for (TimeEntry entry : entries){
            entryToString = entryToString(entry);
            listOfStrings.add(entryToString);
        }
        return listOfStrings;
    }
    
    public String entryToString(TimeEntry entry){
        String entryToString = entry.getDate() + ":     " + entry.getStart() + "   -   " + entry.getEnd();

        return entryToString;
    }

    public String printableString(List<String> entries) {
        return String.join("\n", entries);
    }

    public String hoursWorkedString(int totalMinutes){
        return(totalMinutes/60 + " hours and " + totalMinutes % 60 + " minutes.");
    }

    public float calculateSalary(List<TimeEntry> entries, Contract contract){
        
        float totalSalary = TimeCalculator.calculateSalary(entries, contract);

        return totalSalary;

    }
}
