package model;

import java.time.LocalDate;

public abstract class Transaction {
    protected LocalDate transDate;
    protected double transValue;
    protected String transDescription;

    Transaction(LocalDate date, double value, String desc) {
        this.transDate = date;
        this.transValue = value;
        this.transDescription = desc;
    }

    //apply transaction value to specified balance (+ if income, - if expense)
    public abstract void applyToBalance(Balance bal);
    //return "Expense" if it is an expense object, and return "Income" if it is an income object
    public abstract String getType();

    public String getTransDescription() {
        return transDescription;
    }

    public LocalDate getTransDate() {
        return transDate;
    }

    public Double getTransValue() {
        return transValue;
    }
}
