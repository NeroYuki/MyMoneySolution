package model;

import java.time.LocalDate;

public abstract class Transaction {
    protected LocalDate transDate;
    protected double transValue;
    protected String transDescription;
}
