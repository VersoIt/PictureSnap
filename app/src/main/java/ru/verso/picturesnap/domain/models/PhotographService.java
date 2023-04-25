package ru.verso.picturesnap.domain.models;

public class PhotographService {

    private final String name;
    private final String iconPath;

    public PhotographService(String name, String iconPath) {
        this.name = name;
        this.iconPath = iconPath;
    }

    public String getName() {
        return name;
    }

    public String getIconPath() {
        return iconPath;
    }
}
