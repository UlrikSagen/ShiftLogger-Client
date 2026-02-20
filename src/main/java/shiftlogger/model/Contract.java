package shiftlogger.model;


public record Contract (
    String salaryType,
    int salary,
    float overtimeFactor,
    float overtimeTresholdHours,
    boolean paidBreak,
    float hoursByWeek
){}

