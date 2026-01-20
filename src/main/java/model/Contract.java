package model;

import model.Tariff;

public class Contract {
    private final int salary;
    private final float overtimeFactor;
    private final boolean paidBreak;
    private final Tariff tariff;

    public Contract(int salary, float overtimeFactor, boolean paidBreak, Tariff tariff){
        this.salary = salary;
        this.overtimeFactor = overtimeFactor;
        this.paidBreak = paidBreak;
        this.tariff = tariff;
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
}
