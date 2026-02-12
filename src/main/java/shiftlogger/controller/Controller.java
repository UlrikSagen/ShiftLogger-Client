package controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import model.TimeEntry;
import service.TimeService;



public class Controller {
    
    private final TimeService service;

    public Controller(TimeService service) {
        this.service = service;
    }

    public void addOrEditEntry(LocalDate date, LocalTime start, LocalTime end) {
        service.addOrEdit(date, start, end);
    }

    public List<TimeEntry> getEntriesByMonth(int month, int year) {
        return service.filterByMonth(month, year);
    }

    public int getMinutesByMonth(int month, int year) {
        return service.getTotalMinutes(month, year);
    }

    public int getTotalMinutes() {
        return service.getTotalMinutes();
    }

    public int getMinutesByEntry(TimeEntry entry){
        return (int)service.getMinutesByEntry(entry);
    }

    public void deleteEntry(LocalDate date) {
        service.deleteEntry(date);
    }

    public String getDate(){
        return LocalDate.now().toString();
    }

    public float getSalary(){
        return service.calculateSalary();
    }

    public float getSalary(int year){
        return service.calculateSalary(year);
    }

    public float getSalary(int month, int year){
        return service.calculateSalary(month, year);
    }

    public float getOvertimeSalaryByMonth(int month, int year){
        return service.calculateOverTimeSalary(month, year);
    }

    public float getOvertimeSalary(){
        return service.calculateOverTimeSalary();
    }
    public Duration getOvertime(){
        return service.getOvertime();
    }
    public Duration getOvertime(int month, int year){
        return service.getOvertime(month, year);
    }
    public boolean validateEntry(LocalDate date, LocalTime start, LocalTime end){
        return service.validateEntry(date, start, end);
    }
}

