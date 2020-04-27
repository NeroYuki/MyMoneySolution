package model;

import java.time.LocalDate;

public class Income extends Transaction {
    private String categoryName;

    public Income(LocalDate transDate, double transValue, String transDescription, String categoryName) {
        super(transDate, transValue, transDescription);
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
