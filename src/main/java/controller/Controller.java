package controller;

import model.TimeEntry;
import service.TimeService;
import storage.TimeRepository;
import model.Contract;
import java.time.*;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;



public class Controller {
    
    private final TimeService service;
    private final TimeRepository repo;
    private List<TimeEntry> entries;
    private LocalTime startTime;
    private LocalTime endTime;
    private Contract contract;


    public Controller(TimeService service, TimeRepository repo) {
        this.service = service;
        this.repo = repo;
        this.entries = repo.loadEntries();
        this.contract = repo.loadContract();
    }

    public void addOrEditEntry(LocalDate date, LocalTime start, LocalTime end) {
        this.entries = service.addOrEdit(this.entries, date, start.withSecond(0).withNano(0), end.withSecond(0).withNano(0));
        saveEntries();
    }

    public List<TimeEntry> getAll() {
        return this.entries;
    }

    public List<TimeEntry> getEntriesByMonth(int month, int year) {
        return service.filterByMonth(this.entries, month, year);
    }

    public int getMinutesByMonth(int month, int year) {
        List<TimeEntry> monthlyEntries = getEntriesByMonth(month, year);
        return service.getTotalMinutes(monthlyEntries);
    }

    public int getTotalMinutes() {
        return service.getTotalMinutes(this.entries);
    }

    public void reloadEntries() {
        this.entries = repo.loadEntries();
    }

    public void saveEntries() {
        repo.saveEntries(this.entries);
    }

    public void deleteEntry(LocalDate date) {
        this.entries = service.deleteEntry(this.entries, date);
        saveEntries();
    }

    public String getDate(){
        LocalDate date = LocalDate.now();
        return date.toString();
    }

    public String entriesToString(int month, int year){
        List<String> entries = new ArrayList<>();
        List<TimeEntry> entriesByMonth = getEntriesByMonth(month, year);
        entries = service.getListOfStrings(entriesByMonth);
        String printableString = service.printableString(entries);
        return printableString;
    }

    public String hoursWorkedString(int month, int year){
        return service.hoursWorkedString(getMinutesByMonth(month, year));
    }

    public float getSalary(int month, int year){
        return service.calculateSalary(getEntriesByMonth(month, year), this.contract);
    }

}

