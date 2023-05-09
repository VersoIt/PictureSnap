package ru.verso.picturesnap.domain.models;

public class PhotographerPresentationService {

    private String id;
    private String photographerId;
    private String serviceId;
    private int cost;

    private String name;

    public PhotographerPresentationService(String id, int cost, String photographerId, String serviceId, String name) {
        this.id = id;
        this.cost = cost;
        this.name = name;
        this.photographerId = photographerId;
        this.serviceId = serviceId;
    }

    public PhotographerPresentationService() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPhotographerId(String photographerId) {
        this.photographerId = photographerId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotographerId() {
        return photographerId;
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
