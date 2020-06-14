package model;

import java.time.LocalDate;

public class FinancialGoal {
    private String id = "";
    private String description;
    private int type;
    private double threshold;
    private Balance checkBalance = null;
    private LocalDate startDate;
    private LocalDate expireDate;

    public FinancialGoal(String desc, int type, double threshold, LocalDate startDate, LocalDate expireDate, Balance checkBalance) {
        setDescription(desc);
        setType(type);
        setThreshold(threshold);
        setStartDate(startDate);
        setExpireDate(expireDate);
        if (type == 3) {
            setCheckBalance(checkBalance);
        }
    }

    public FinancialGoal(String id, String desc, int type, double threshold, LocalDate startDate, LocalDate expireDate, Balance checkBalance) {
        this(desc, type, threshold, startDate, expireDate, checkBalance);
        setId(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCheckBalance(Balance checkBalance) {
        this.checkBalance = checkBalance;
    }

    public Balance getCheckBalance() {
        return checkBalance;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public boolean checkGoal(double value) {
        if (type == 2) {
            if (value > threshold) return false;
            return true;
        }
        else if (type == 1 || type == 3) {
            if (value < threshold) return false;
            return true;
        }
        return false;
    }
}
