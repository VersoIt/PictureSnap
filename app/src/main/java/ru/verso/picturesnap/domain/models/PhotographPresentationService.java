package ru.verso.picturesnap.domain.models;

public class PhotographPresentationService {

    private String id;
    private String photographId;
    private String serviceId;
    private int cost;

    private String name;

    public PhotographPresentationService(String id, int cost, String photographId, String serviceId, String name) {
        this.id = id;
        this.cost = cost;
        this.name = name;
        this.photographId = photographId;
        this.serviceId = serviceId;
    }

    public PhotographPresentationService() {
    }

    public String getPhotographId() {
        return photographId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }
}
