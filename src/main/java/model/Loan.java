package model;

public class Loan {
    private String name;
    private String description;
    private double interest;
    private double term;
    //Should be enum { DAILY, WEEKLY, MONTHLY, QUARTERLY, YEARLY }
    private Object paymentInterval;
}
