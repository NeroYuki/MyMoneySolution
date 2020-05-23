package model;

public class Category {
    String id = "";
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

    public Category(String id, String name, String desc, String path, int type) {
        this(name, desc, path, type);
        setId(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
