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


    public Income(LocalDate date, double value, String desc, Category category,Balance balance){
        super(date, value, desc,balance);
        this.category = category;
    }

    public Income(LocalDate date, double value, String desc,String categoryName,Balance balance){
        super(date, value, desc,balance);
        this.category = new Category(categoryName, "", "", 1);
    }

    public Income(String id, LocalDate date, double value, String desc, String categoryName,Balance balance) {
        super(id, date, value, desc,balance);
        this.category = new Category(categoryName, "", "", 1);
    }

    public Income(String id, LocalDate date, double value, String desc, Category category,Balance balance) {
        super(id, date, value, desc,balance);
        this.category = category;
    }


    public void applyToBalance() {
        this.applyingBalance.setValue(this.applyingBalance.getValue()+this.transValue);
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
