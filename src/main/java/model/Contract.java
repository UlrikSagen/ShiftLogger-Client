package model;


public class Contract {
    private final String salaryType;
    private final int salary;
    private final float overtimeFactor;
    private final boolean paidBreak;
    private final float hoursByWeek;
    private final Tariff tariff;

    public Contract(String salaryType, int salary, float overtimeFactor, boolean paidBreak, float hoursByWeek, Tariff tariff){
        this.salaryType = salaryType;
        this.salary = salary;
        this.overtimeFactor = overtimeFactor;
        this.paidBreak = paidBreak;
        this.hoursByWeek = hoursByWeek;
        this.tariff = tariff;
    }

    public String getSalaryType(){
        return this.salaryType;
    }
    public int getSalary(){
        return this.salary;
    }
    public float getOvertimeFactor(){
        return this.overtimeFactor;
    }
    public boolean hasPaidBreak(){
        return this.paidBreak;
    }
    public Tariff getTariff(){
        return this.tariff;
    }
    public float getHoursByWeek(){
        return this.hoursByWeek;
    }
}
