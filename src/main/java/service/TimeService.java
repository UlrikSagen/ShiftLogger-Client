package service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import model.Contract;
import model.TimeEntry;
import storage.TimeRepository;


public class TimeService {

    private final TimeRepository repo;
    private Contract contract;
    private List<TimeEntry> entries;

    public TimeService(TimeRepository repo){
        this.repo = repo;
        this.entries = repo.loadEntries();
        this.contract = repo.loadContract();
    }
    

    public List <TimeEntry> getEntries(){
        return List.copyOf(this.entries);
    }
    //method for adding or editing entry
    public void addOrEdit(LocalDate date, LocalTime start, LocalTime end){

        List<TimeEntry> out = new ArrayList<>();
        boolean replaced = false;

        for (TimeEntry entry : this.entries) {
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
        this.entries = out;
        saveEntries();
    }

    //method for deleting entry by date
    public void deleteEntry(LocalDate date){
        List<TimeEntry> out = new ArrayList<>();

        for (TimeEntry entry : this.entries) {
            if (!entry.getDate().equals(date)){
                out.add(entry);
            }
        }
        this.entries = out;
        saveEntries();
    }

    //method for calculating total minutes from list of entries
    public int getTotalMinutes(){
        return TimeCalculator.getTotalMinutes(this.entries);
    }

    public int getTotalMinutes(int month, int year){
        return TimeCalculator.getTotalMinutes(filterByMonth(month, year));
    }

    //method for filtering entries by month and year
    public List<TimeEntry> filterByMonth(int month, int year){
        List<TimeEntry> filtered = new ArrayList<>();
        for (TimeEntry entry : this.entries) {
            if (entry.getDate().getMonthValue() == month && entry.getDate().getYear() == year) {
                filtered.add(entry);
            }
        }

        return filtered;
    }

    //method for formatting single entry to string

    //method for formatting list of entries to printable string

    //method for formatting hours worked string
    public String hoursWorkedString(int totalMinutes){
        return(totalMinutes/60 + " hours and " + totalMinutes % 60 + " minutes.");
    }

    //method for calculating total salary
    public float calculateSalary(){
        Duration overTime = Duration.ZERO;
        for (TimeEntry entry : this.entries){
            overTime = overTime.plus(TimeCalculator.calculateOvertimeHours(Duration.between(entry.getStart(), entry.getEnd())));
        }
        float totalSalary = TimeCalculator.calculateSalary(this.entries, this.contract, overTime);

        return totalSalary;
    }

    public float calculateSalary(int month, int year){
        Duration overTime = Duration.ZERO;
        for (TimeEntry entry : this.entries){
            if (entry.getDate().getMonthValue() == month && entry.getDate().getYear() == year) {
                overTime = overTime.plus(TimeCalculator.calculateOvertimeHours(Duration.between(entry.getStart(), entry.getEnd())));
            }
        }
        float totalSalary = TimeCalculator.calculateSalary(filterByMonth(month, year), this.contract, overTime);

        return totalSalary;
    }

    //method for calculating overtime salary
    public float calculateOverTimeSalary(){
        Duration totalOvertime = Duration.ZERO;
        for (TimeEntry entry : this.entries){
            totalOvertime = totalOvertime.plus(TimeCalculator.calculateOvertimeHours(Duration.between(entry.getStart(), entry.getEnd())));
        }
        float overTimeSalary = TimeCalculator.calculateOvertimeSalary(this.contract, totalOvertime);
        
        return overTimeSalary;
    }

    public float calculateOverTimeSalary(int month, int year){
        Duration totalOvertime = Duration.ZERO;
        for (TimeEntry entry : filterByMonth(month, year)){
            totalOvertime = totalOvertime.plus(TimeCalculator.calculateOvertimeHours(Duration.between(entry.getStart(), entry.getEnd())));
        }
        float overTimeSalary = TimeCalculator.calculateOvertimeSalary(this.contract, totalOvertime);
        
        return overTimeSalary;
    }

    public void saveEntries(){
        repo.saveEntries(this.entries);
    }
}
