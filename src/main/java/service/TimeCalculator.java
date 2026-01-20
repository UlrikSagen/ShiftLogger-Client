package service;
import model.TimeEntry;
import model.Contract;

import java.time.*;
import java.util.List;
import java.util.ArrayList;


public class TimeCalculator {
    public static int getTotalMinutes(List<TimeEntry> entries){
        int totalMinutes = 0;
        for (TimeEntry entry : entries) {
            Duration duration = calculateBreak(Duration.between(entry.getStart(), entry.getEnd()));
            totalMinutes += duration.toMinutes();
        }
        return totalMinutes;
    }

    private static Duration calculateBreak(Duration duration) {
        if (duration.toMinutes() > 300) {
            duration = duration.minusMinutes(30);
        }
        return duration;
    }

    private static Duration calculateOvertimeHours(Duration duration){
        if(duration.toMinutes()>600){
            duration = duration.minusMinutes(600);
            return duration;
        }
        return Duration.ofMinutes(0);
    }

    public static float calculateSalary(List<TimeEntry> entries, Contract contract){
        int salary = contract.getSalary();
        float totalSalary = 0;
        int totalMinutes = getTotalMinutes(entries);
        
        totalSalary = (totalMinutes * salary)/60;

        return totalSalary;

    }

    
}
