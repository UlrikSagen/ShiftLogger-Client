package shiftlogger.model;


public record Contract (
    String salaryType,
    int salary,
    float overtimeFactor,
    boolean paidBreak,
    float hoursByWeek,
    Tariff tariff
){}

