package model;

public class Saving {
    private String name;
    private String description;
    private double interest;
    private int activeTimeSpan; //in days
    //Should be enum { DAILY, WEEKLY, MONTHLY, QUARTERLY, YEARLY }
    private Object interestInterval;
    private double currentValue;
}
