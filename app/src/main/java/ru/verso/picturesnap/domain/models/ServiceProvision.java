package ru.verso.picturesnap.domain.models;

public class ServiceProvision {

    private final int id;
    private final int photographId;
    private final int serviceId;
    private final int cost;

    public ServiceProvision(int id, int photographId, int serviceId, int cost) {
        this.id = id;
        this.photographId = photographId;
        this.serviceId = serviceId;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public int getPhotographId() {
        return photographId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public int getCost() {
        return cost;
    }
}
