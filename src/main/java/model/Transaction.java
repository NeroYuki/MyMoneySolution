package model;

import java.time.LocalDate;

public abstract class Transaction {
    protected String id = "";
    protected LocalDate transDate;
    protected double transValue;
    protected String transDescription;
    protected Balance applyingBalance;

    public Transaction(LocalDate date, double value, String desc) {
        this.transDate = date;
        this.transValue = value;
        this.transDescription = desc;
    }

    public Transaction(String id, LocalDate date, double value, String desc) {
        this(date, value, desc);
        setId(id);
    }

    public Transaction(LocalDate date, double value, String desc, Balance applyBal) {
        this(date, value, desc);
        setApplyingBalance(applyBal);
    }

    public Transaction(String id, LocalDate date, double value, String desc, Balance applyBal) {
        this(date, value, desc, applyBal);
        setId(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setApplyingBalance(Balance applyingBalance) {
        this.applyingBalance = applyingBalance;
    }

    public Balance getApplyingBalance() {
        return applyingBalance;
    }

    //apply transaction value to specified balance (+ if income, - if expense)
    public abstract void applyToBalance();
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
