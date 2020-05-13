package model;

public class Category {
    long id = 0;
    String name;
    String description;
    String iconPath;
    int type;

    public Category(String name, String desc, String path, int type) {
        setName(name);
        setDescription(desc);
        setIconPath(path);
        setType(type);
    }

    public Category(long id, String name, String desc, String path, int type) {
        this(name, desc, path, type);
        setId(id);
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public void setType(int type) {
        this.type = type;
    }
}
