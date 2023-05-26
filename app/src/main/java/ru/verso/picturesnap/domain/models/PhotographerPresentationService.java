package ru.verso.picturesnap.domain.models;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        return Objects.hash(id, photographerId, serviceId, cost, name);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (other instanceof PhotographerPresentationService) {
            PhotographerPresentationService object = (PhotographerPresentationService) other;

            return id.equals(object.id) &&
                    photographerId.equals(object.photographerId) &&
                    serviceId.equals(object.serviceId) &&
                    cost == object.cost &&
                    name.equals(object.name);
        }

        return false;
    }
}
