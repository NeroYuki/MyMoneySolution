package model;

public class Balance {
    private long id = 0;
    private String name;
    private String description;
    private double value;

    public Balance(String name, String desc, double value) {
        setName(name);
        setDescription(desc);
        setValue(value);
    }

    public Balance(long id, String name, String desc, double value) {
        this(name, desc, value);
        setId(id);
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
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

    void setValue(double val) {
        this.value = val;
    }
}
