package ru.verso.picturesnap.domain.models;

public class PhotographerService {

    private String id;
    private String name;
    private String iconPath;

    public PhotographerService(String id, String name, String iconPath) {
        this.id = id;
        this.name = name;
        this.iconPath = iconPath;
    }

    public PhotographerService() {

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
