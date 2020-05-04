package model;

public class Loan {
    private String name;
    private String description;
    private double interestRate;
    private int activeTimeSpan; //in days
    //TODO: Should be enum { DAILY, WEEKLY, MONTHLY, QUARTERLY, YEARLY }
    private Object interestInterval;
    private Object paymentInterval;
    private double baseValue;
    private double currentValue;

    void addToBalance(Balance bal) {
        double newValue = bal.getValue() + this.baseValue;
        bal.setValue(newValue);
    }

    void loanPayment(Balance bal, double amount) {
        double newBalanceValue = bal.getValue() - amount;
        this.currentValue -= amount;
        bal.setValue(newBalanceValue);
    }

    void applyInterest() {
        this.currentValue *= interestRate;
    }
}
