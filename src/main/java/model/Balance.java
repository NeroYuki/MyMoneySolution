package model;

public class Balance {
    private String id = "";
    private String name;
    private String description;
    private double value;

    public Balance(String name, String desc, double value) {
        setName(name);
        setDescription(desc);
        setValue(value);
    }

    public Balance(String id, String name, String desc, double value) {
        this(name, desc, value);
        setId(id);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
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

    public void setValue(double val) {
        this.value = val;
    }
}
