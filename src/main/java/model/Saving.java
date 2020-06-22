package model;

import helper.IntervalEnum;

import java.time.LocalDate;

public class Saving {
    private String id = "";
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
    public Saving (String id, String name, String desc, double interest, LocalDate creationDate, int activeTime, IntervalEnum.INTERVAL interestInterval, double baseValue, double currentValue) {
        this(name, desc, interest, creationDate, activeTime, interestInterval, baseValue, currentValue);
        setId(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setActiveTimeSpan(int activeTimeSpan) {
        this.activeTimeSpan = activeTimeSpan;
    }

    public int getActiveTimeSpan() {
        return activeTimeSpan;
    }

    public void setInterestInterval(IntervalEnum interestInterval) {
        this.interestInterval = interestInterval;
    }

    public IntervalEnum getInterestInterval() {
        return interestInterval;
    }

    public void setBaseValue(double baseValue) {
        this.baseValue = baseValue;
    }

    public double getBaseValue() {
        return baseValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    Expense depositMoney(Balance bal, double amount, LocalDate date, String desc, Category category) {
        Expense result = new Expense(date, amount, desc, category,bal);
        result.applyToBalance();
        this.currentValue += amount;
        return result;
    }

    Income withdrawMoney(Balance bal, double amount, LocalDate date, String desc, Category category) {
        Income result = new Income(date, amount, desc, category,bal);
        result.applyToBalance();
        this.currentValue -= amount;
        return result;
    }

    void applyInterest() {
        this.currentValue *= interestRate;
    }
}
