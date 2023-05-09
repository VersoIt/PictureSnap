package ru.verso.picturesnap.domain.models;

public class Favorite {

    private final int id;
    private final int photographerId;
    private final int clientId;

    public Favorite(int id, int photographerId, int clientId) {
        this.id = id;
        this.photographerId = photographerId;
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public int getPhotographerId() {
        return photographerId;
    }

    public int getClientId() {
        return clientId;
    }
}
