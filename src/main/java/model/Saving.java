package model;

public class Saving {
    private String name;
    private String description;
    private double interestRate;
    private int activeTimeSpan; //in days
    //TODO: Should be enum { DAILY, WEEKLY, MONTHLY, QUARTERLY, YEARLY }
    private Object interestInterval;
    private double baseValue;
    private double currentValue;

    void withdrawMoney(Balance bal, int amount) {
        double newBalanceValue = bal.getValue() + amount;
        this.currentValue -= amount;
        bal.setValue(newBalanceValue);
    }

    void applyInterest() {
        this.currentValue *= interestRate;
    }
}
