package model;

public class Balance {
    private String name;
    private String description;
    private double value;

    public Balance(String name, String desc, double value) {
        setName(name);
        setDescription(desc);
        setValue(value);
    }

    public double getValue() {
        return value;
    }

    void setName(String name) {
        this.name = name;
    }

    void setDescription(String description) {
        this.description = description;
    }

    void setValue(double val) {
        this.value = val;
    }
}
