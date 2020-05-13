package model;

import helper.IntervalEnum;

import java.time.LocalDate;

public class Loan {
    private long id = 0;
    private String name;
    private String description;
    private double interestRate;
    private int activeTimeSpan; //in days
    private LocalDate creationDate;
    private IntervalEnum interestInterval;
    private IntervalEnum paymentInterval;
    private double baseValue;
    private double currentValue;

    public Loan(String name, String desc, double interest, LocalDate creationDate, int activeTime, IntervalEnum.INTERVAL interestInterval, IntervalEnum.INTERVAL paymentInterval, double baseValue, double currentValue) {
        setName(name);
        setDescription(desc);
        setInterestRate(interest);
        setCreationDate(creationDate);
        setActiveTimeSpan(activeTime);
        setInterestInterval(new IntervalEnum(interestInterval));
        setPaymentInterval(new IntervalEnum(paymentInterval));
        setBaseValue(baseValue);
        setCurrentValue(currentValue);
    }

    public Loan(long id, String name, String desc, double interest, LocalDate creationDate, int activeTime, IntervalEnum.INTERVAL interestInterval, IntervalEnum.INTERVAL paymentInterval, double baseValue, double currentValue) {
        this(name, desc, interest, creationDate, activeTime, interestInterval, paymentInterval, baseValue, currentValue);
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

    public void setPaymentInterval(IntervalEnum paymentInterval) {
        this.paymentInterval = paymentInterval;
    }

    public void setBaseValue(double baseValue) {
        this.baseValue = baseValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    Income addToBalance(Balance bal, LocalDate date, String desc, Category category) {
        Income result = new Income(date, baseValue, desc , category);
        result.applyToBalance(bal);
        return result;
    }

    Expense loanPayment(Balance bal, double amount, LocalDate date, String desc, Category category) {
        Expense result = new Expense(date, amount, desc, category);
        result.applyToBalance(bal);
        this.currentValue -= amount;
        return result;
    }

    void applyInterest() {
        this.currentValue *= interestRate;
    }
}
