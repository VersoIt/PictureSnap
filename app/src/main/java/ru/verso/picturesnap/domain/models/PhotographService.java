package ru.verso.picturesnap.domain.models;

public class PhotographService {

    private final String name;
    private final String iconPath;
    private final int cost;

    public PhotographService(String name, String iconPath, int cost) {
        this.name = name;
        this.iconPath = iconPath;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public String getIconPath() {
        return iconPath;
    }
}
