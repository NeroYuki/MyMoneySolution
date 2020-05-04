package model;

import java.time.LocalDate;

public class Expense extends Transaction {
    private String categoryName;

    public Expense(LocalDate date, double value, String desc, String category) {
        super(date, value, desc);
        this.categoryName = category;
    }

    public void applyToBalance(Balance bal) {
        double newValue = bal.getValue() - this.transValue;
        bal.setValue(newValue);
    }

    public String getCategoryName() {
        return categoryName;
    }
}
