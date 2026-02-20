package shiftlogger.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import shiftlogger.http.ApiClient;
import shiftlogger.model.Contract;
import shiftlogger.model.TimeEntry;
import shiftlogger.model.User;



public class TimeService {

    private Contract contract;
    private final ApiClient api;
    private User user;
    private List<TimeEntry> entries = new ArrayList<>();
    private boolean loggedIn = false;
    

    public TimeService(ApiClient api){
        this.api = api;
    }

    public void init(User user) throws Exception{
        this.user = user;
        this.api.setToken(user.token());
        this.entries = api.getEntry(Optional.empty(), Optional.empty());
        this.entries.sort(Comparator.comparing(TimeEntry::date));
        this.contract = user.contract();
        this.loggedIn = true;
    }

    public void logout(){
        this.api.setToken(null);
        this.loggedIn = false;
    }

    public boolean loggedIn(){
        return loggedIn;
    }

    public List<TimeEntry> getEntries(){
        return List.copyOf(this.entries);
    }
    //method for adding or editing entry
    public void postEntry(LocalDate date, LocalTime start, LocalTime end) throws Exception{
        api.postEntry(date, start, end);
        this.entries = api.getEntry(Optional.empty(), Optional.empty());
        this.entries.sort(Comparator.comparing(TimeEntry::date));
    }

    public void updateEntry(UUID id, LocalDate date, LocalTime start, LocalTime end) throws Exception{
        this.entries.removeIf(entry -> entry.id().equals(id));
        this.entries.add(api.updateEntry(id, date, start, end));
        //this.entries = api.getEntry(Optional.empty(), Optional.empty());
        this.entries.sort(Comparator.comparing(TimeEntry::date));
    }

    //method for deleting entry by id.
    public void deleteEntry(UUID id)throws Exception{
        List<TimeEntry> out = new ArrayList<>();
        api.deleteEntry(id);
        for (TimeEntry entry : this.entries) {
            if (!entry.id().equals(id)){
                out.add(entry);
            }
        }
        this.entries = out;
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
            if (entry.date().getMonthValue() == month && entry.date().getYear() == year) {
                filtered.add(entry);
            }
        }

        return filtered;
    }

    public List<TimeEntry> filterByYear(int year){
        List<TimeEntry> filtered = new ArrayList<>();
        for (TimeEntry entry : this.entries) {
            if (entry.date().getYear() == year) {
                filtered.add(entry);
            }
        }

        return filtered;
    }


    public long getMinutesByEntry(TimeEntry entry){
        return TimeCalculator.calculateBreak(Duration.between(entry.start(), entry.end())).toMinutes();
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

    public Duration getOvertime(int month, int year){
        Duration totalOvertime = Duration.ZERO;
        for (TimeEntry entry : filterByMonth(month, year)){
            totalOvertime = totalOvertime.plus(TimeCalculator.calculateOvertimeHours(Duration.between(entry.start(), entry.end())));
        }
        return totalOvertime;
    }

    public Duration getOvertime(int year){
        Duration totalOvertime = Duration.ZERO;
        for (TimeEntry entry : filterByYear(year)){
            totalOvertime = totalOvertime.plus(TimeCalculator.calculateOvertimeHours(Duration.between(entry.start(), entry.end())));
        }
        return totalOvertime;
    }

    public Duration getOvertime(){
        Duration totalOvertime = Duration.ZERO;
        for (TimeEntry entry : this.entries){
            totalOvertime = totalOvertime.plus(TimeCalculator.calculateOvertimeHours(Duration.between(entry.start(), entry.end())));
        }
        return totalOvertime;
    }

    public boolean validateEntry(LocalDate date, LocalTime start, LocalTime end){
        if(date.isAfter(LocalDate.now())){
            return false;
        }
        else if(!start.isBefore(end)){
            return false;
        }
        else{
            return true;
        }
    }
}
