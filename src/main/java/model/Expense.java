package model;

import java.time.LocalDate;

public class Expense extends Transaction {
    private Category category;

    //TODO: change to category instead of name
    public Expense(LocalDate date, double value, String desc, String categoryName) {
        super(date, value, desc);
        this.category = new Category(categoryName, "", "", 2);
    }

    public void applyToBalance(Balance bal) {
        double newValue = bal.getValue() - this.transValue;
        bal.setValue(newValue);
    }

    public String getCategoryName() {
        return category.name;
    }
}
