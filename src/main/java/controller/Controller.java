package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import model.TimeEntry;
import service.TimeService;
import storage.TimeRepository;



public class Controller {
    
    private final TimeService service;

    public Controller(TimeService service) {
        this.service = service;
    }

    public void addOrEditEntry(LocalDate date, LocalTime start, LocalTime end) {
        service.addOrEdit(date, start.withSecond(0).withNano(0), end.withSecond(0).withNano(0));
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


    public void deleteEntry(LocalDate date) {
        service.deleteEntry(date);
    }

    public String getDate(){
        return LocalDate.now().toString();
    }

    public String entriesToString(int month, int year){
        List<TimeEntry> entriesByMonth = getEntriesByMonth(month, year);
        List<String> entriesByMonthList = getListOfStrings(entriesByMonth);
        String printableString = printableString(entriesByMonthList);
        return printableString;
    }

    public List<String> getListOfStrings(List<TimeEntry> entriesByMonth){
        List<String> listOfStrings =  new ArrayList<>();
        String entryToString;
        for (TimeEntry entry : entriesByMonth){
            entryToString = entry.getDate() + ":     " + entry.getStart() + "   -   " + entry.getEnd();
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

    public String hoursWorkedString(int month, int year){
        return service.hoursWorkedString(getMinutesByMonth(month, year));
    }

    public float getSalaryByMonth(int month, int year){
        return service.calculateSalary(month, year);
    }

    public float getSalary(){
        return service.calculateSalary();
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

    //public Timer timer(){
    //    Timer timer = new Timer();
    //    return timer;
    //}

}

