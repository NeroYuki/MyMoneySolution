package model;

import java.time.LocalDate;

public class Income extends Transaction {
    private Category category;

    //TODO: change to category instead of name
    public Income(String id, LocalDate date, double value, String desc, String categoryName) {
        super(id, date, value, desc);
        this.category = new Category(categoryName, "", "", 1);
    }

    public Income(String id, LocalDate date, double value, String desc, Category category) {
        super(id, date, value, desc);
        this.category = category;
    }

    public Income(LocalDate date, double value, String desc, String categoryName) {
        super(date, value, desc);
        this.category = new Category(categoryName, "", "", 1);
    }

    public Income(LocalDate date, double value, String desc, Category category) {
        super(date, value, desc);
        this.category = category;
    }

    public void applyToBalance(Balance bal) {
        double newValue = bal.getValue() + this.transValue;
        this.applyingBalance = bal;
        bal.setValue(newValue);
    }

    public String getType() {
        return "Income";
    }

    public Category getCategory() {
        return category;
    }

    public String getCategoryName() {
        return category.name;
    }
}
