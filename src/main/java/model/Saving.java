package model;

import helper.IntervalEnum;

import java.time.LocalDate;

public class Saving {
    private long id = 0;
    private String name;
    private String description;
    private double interestRate;
    private LocalDate creationDate;
    private int activeTimeSpan; //in days
    private IntervalEnum interestInterval;
    private double baseValue;
    private double currentValue;

    public Saving (String name, String desc, double interest, LocalDate creationDate, int activeTime, IntervalEnum.INTERVAL interestInterval, double baseValue, double currentValue) {
        setName(name);
        setDescription(desc);
        setInterestRate(interest);
        setCreationDate(creationDate);
        setActiveTimeSpan(activeTime);
        setInterestInterval(new IntervalEnum(interestInterval));
        setBaseValue(baseValue);
        setCurrentValue(currentValue);
    }
    public Saving (long id, String name, String desc, double interest, LocalDate creationDate, int activeTime, IntervalEnum.INTERVAL interestInterval, double baseValue, double currentValue) {
        this(name, desc, interest, creationDate, activeTime, interestInterval, baseValue, currentValue);
        setId(id);
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setActiveTimeSpan(int activeTimeSpan) {
        this.activeTimeSpan = activeTimeSpan;
    }

    public void setInterestInterval(IntervalEnum interestInterval) {
        this.interestInterval = interestInterval;
    }

    public void setBaseValue(double baseValue) {
        this.baseValue = baseValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    void withdrawMoney(Balance bal, int amount) {
        double newBalanceValue = bal.getValue() + amount;
        this.currentValue -= amount;
        bal.setValue(newBalanceValue);
    }

    void applyInterest() {
        this.currentValue *= interestRate;
    }
}
