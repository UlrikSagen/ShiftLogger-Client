package shiftlogger.controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import shiftlogger.service.TimeService;
import shiftlogger.storage.SettingsLoader;
import shiftlogger.service.AuthService;
import shiftlogger.model.Settings;
import shiftlogger.model.TimeEntry;
import shiftlogger.model.User;

public class Controller {
    
    private final TimeService service;
    private final AuthService authService;

    public Controller(TimeService service, AuthService authService) {
        this.service = service;
        this.authService = authService;
    }

    public void postEntry(LocalDate date, LocalTime start, LocalTime end) throws Exception {
        service.postEntry(date, start, end);
    }

    public void updateEntry(UUID id, LocalDate date, LocalTime start, LocalTime end) throws Exception {
        service.updateEntry(id, date, start, end);
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

    public void deleteEntry(UUID id) throws Exception{
        service.deleteEntry(id);
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

    //AUTHENTICATION
    public boolean register(String username, String pwd){
        return authService.registerUser(username, pwd);
    }

    public void login(String username, String pwd) throws Exception {
        User user = authService.login(username, pwd);
        service.init(user);
    }

    public void logout(){
        service.logout();
    }

    public boolean loggedIn(){
        return service.loggedIn();
    }

    //SETTINGS
    public void saveSettings(Settings settings){
        SettingsLoader.saveSettings(settings);
    }
    
    public Settings loadSettings(){
        return SettingsLoader.loadSettings();
    }
}

