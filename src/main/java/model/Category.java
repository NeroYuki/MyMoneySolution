package model;

public class Category {
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
