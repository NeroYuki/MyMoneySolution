package model;

public class Loan {
    private String name;
    private String description;
    private double interest;
    private int activeTimeSpan; //in days
    //Should be enum { DAILY, WEEKLY, MONTHLY, QUARTERLY, YEARLY }
    private Object interestInterval;
    private Object paymentInterval;
    private double currentValue;
}
