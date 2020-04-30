package model;

import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public abstract class Transaction {
    protected LocalDate transDate;
    protected Double transValue;
    protected String transDescription;

    // add constructor
    public Transaction() {

    }

    // add parameter constructor
    public Transaction(LocalDate transDate, double transValue, String transDescription) {
        this.transDate = transDate;
        this.transValue = transValue;
        this.transDescription = transDescription;
    }

    public LocalDate getTransDate() {
        return transDate;
    }

    public void setTransDate(LocalDate transDate) {
        this.transDate = transDate;
    }

    public Double getTransValue() {
        return transValue;
    }

    public void setTransValue(Double transValue) {
        this.transValue = transValue;
    }

    public String getTransDescription() {
        return transDescription;
    }

    public void setTransDescription(String transDescription) {
        this.transDescription = transDescription;
    }

}
