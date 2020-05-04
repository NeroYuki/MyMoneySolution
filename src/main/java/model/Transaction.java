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

    public abstract void applyToBalance(Balance bal);

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
