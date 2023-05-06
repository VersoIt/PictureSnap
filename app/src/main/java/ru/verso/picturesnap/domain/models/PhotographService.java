package ru.verso.picturesnap.domain.models;

public class PhotographService {

    private String id;
    private String name;
    private String iconPath;

    public PhotographService(String id, String name, String iconPath) {
        this.id = id;
        this.name = name;
        this.iconPath = iconPath;
    }

    public PhotographService() {

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIconPath() {
        return iconPath;
    }
}
