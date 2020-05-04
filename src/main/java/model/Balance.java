package model;

public class Balance {
    private String name;
    private String description;
    private double value;

    public double getValue() {
        return value;
    }

    void setValue(double val) {
        this.value = val;
    }
}
