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
                out.add(new TimeEntry(date, start.withSecond(0).withNano(0), end.withSecond(0).withNano(0)));
                replaced = true;
            } else {
                out.add(entry);
            }
        }

        if (!replaced) {
            out.add(new TimeEntry(date, start.withSecond(0).withNano(0), end.withSecond(0).withNano(0)));
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

    public List<TimeEntry> filterByYear(int year){
        List<TimeEntry> filtered = new ArrayList<>();
        for (TimeEntry entry : this.entries) {
            if (entry.getDate().getYear() == year) {
                filtered.add(entry);
            }
        }

        return filtered;
    }


    public long getMinutesByEntry(TimeEntry entry){
        return TimeCalculator.calculateBreak(Duration.between(entry.getStart(), entry.getEnd())).toMinutes();
    }

    //method for calculating total salary
    public float calculateSalary(){
        Duration overTime = getOvertime();
        float totalSalary = TimeCalculator.calculateSalary(this.entries, this.contract, overTime);

        return totalSalary;
    }

    public float calculateSalary(int year){
        Duration totalOvertime = getOvertime(year);
        float totalSalary = TimeCalculator.calculateSalary(filterByYear(year), this.contract, totalOvertime);

        return totalSalary;
    }

    public float calculateSalary(int month, int year){
        Duration totalOvertime = getOvertime(month, year);
        float totalSalary = TimeCalculator.calculateSalary(filterByMonth(month, year), this.contract, totalOvertime);

        return totalSalary;
    }

    //method for calculating overtime salary
    public float calculateOverTimeSalary(){
        Duration totalOvertime = getOvertime();
        float overTimeSalary = TimeCalculator.calculateOvertimeSalary(this.contract, totalOvertime);
        
        return overTimeSalary;
    }

    //method for calculatin overtime salary by given month and year
    public float calculateOverTimeSalary(int month, int year){
        Duration totalOvertime = getOvertime(month, year);
        float overTimeSalary = TimeCalculator.calculateOvertimeSalary(this.contract, totalOvertime);
        
        return overTimeSalary;
    }

    public void saveEntries(){
        repo.saveEntries(this.entries);
    }

    public Duration getOvertime(int month, int year){
        Duration totalOvertime = Duration.ZERO;
        for (TimeEntry entry : filterByMonth(month, year)){
            totalOvertime = totalOvertime.plus(TimeCalculator.calculateOvertimeHours(Duration.between(entry.getStart(), entry.getEnd())));
        }
        return totalOvertime;
    }

    public Duration getOvertime(int year){
        Duration totalOvertime = Duration.ZERO;
        for (TimeEntry entry : filterByYear(year)){
            totalOvertime = totalOvertime.plus(TimeCalculator.calculateOvertimeHours(Duration.between(entry.getStart(), entry.getEnd())));
        }
        return totalOvertime;
    }

    public Duration getOvertime(){
        Duration totalOvertime = Duration.ZERO;
        for (TimeEntry entry : this.entries){
            totalOvertime = totalOvertime.plus(TimeCalculator.calculateOvertimeHours(Duration.between(entry.getStart(), entry.getEnd())));
        }
        return totalOvertime;
    }

    public boolean validateEntry(LocalDate date, LocalTime start, LocalTime end){
        if(date.isAfter(LocalDate.now())){
            return false;
        }
        else if(start.isAfter(end)){
            return false;
        }
        else{
            return true;
        }
    }
}
