package model;

import java.time.LocalDate;

public abstract class Transaction {
    protected long id = 0;
    protected LocalDate transDate;
    protected double transValue;
    protected String transDescription;
    protected Balance applyingBalance;

    Transaction(LocalDate date, double value, String desc) {
        this.transDate = date;
        this.transValue = value;
        this.transDescription = desc;
    }

    Transaction(long id, LocalDate date, double value, String desc) {
        this(date, value, desc);
        setId(id);
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Balance getApplyingBalance() {
        return applyingBalance;
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
