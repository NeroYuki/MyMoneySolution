package model;

import helper.IntervalEnum;

import java.time.LocalDate;

public class Loan {
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

    void addToBalance(Balance bal) {
        double newValue = bal.getValue() + this.baseValue;
        bal.setValue(newValue);
    }

    void loanPayment(Balance bal, double amount) {
        double newBalanceValue = bal.getValue() - amount;
        this.currentValue -= amount;
        bal.setValue(newBalanceValue);
    }

    void applyInterest() {
        this.currentValue *= interestRate;
    }
}
