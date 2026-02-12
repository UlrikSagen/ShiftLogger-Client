package service;
import java.time.Duration;
import java.util.List;

import model.Contract;
import model.TimeEntry;


public class TimeCalculator {
    public static int getTotalMinutes(List<TimeEntry> entries){
        int totalMinutes = 0;
        for (TimeEntry entry : entries) {
            Duration duration = calculateBreak(Duration.between(entry.getStart(), entry.getEnd()));
            totalMinutes += duration.toMinutes();
        }
        return totalMinutes;
    }

    public static Duration calculateBreak(Duration duration) {
        if (duration.toMinutes() > 300) {
            duration = duration.minusMinutes(30);
        }
        return duration;
    }

    public static Duration calculateOvertimeHours(Duration duration){
        if(duration.toMinutes()>540){
            duration = duration.minusMinutes(540);
            return duration;
        }
        return Duration.ZERO;
    }

    public static float calculateSalary(List<TimeEntry> entries, Contract contract, Duration overTime){
        float salary = contract.getSalary();
        float totalMinutes = getTotalMinutes(entries);
        float overtimeHours = overTime.toMinutes()/60.0f;
        float overTimeSalary =  overtimeHours * salary * contract.getOvertimeFactor();
        float totalSalary = overTimeSalary + ((totalMinutes * salary)/60);  


        return totalSalary;
    }

    public static float calculateOvertimeSalary(Contract contract, Duration overTime){
        float salary = contract.getSalary();
        float overtimeHours = overTime.toMinutes()/60.0f;
        float overTimeSalary =  overtimeHours * salary * contract.getOvertimeFactor();
        return overTimeSalary;
    }    
}
