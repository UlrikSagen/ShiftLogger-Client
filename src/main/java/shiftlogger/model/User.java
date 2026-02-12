package model;

import java.util.ArrayList;

import model.TimeEntry;
import service.TimeService;
import service.AuthService;


public class User {
    private final String username;
    private ArrayList<TimeEntry> entries;
    private String accsessToken;

    public User(String username){
        this.username = username;
        this.accsessToken = AuthService.getAccessToken(this);
    }

    //GETTERS
    public String getUsername(){
        return username;
    }

    public ArrayList<TimeEntry> getEntries(){
        return entries;
    }

}
