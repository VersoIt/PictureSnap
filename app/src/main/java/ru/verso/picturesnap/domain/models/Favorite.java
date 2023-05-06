package ru.verso.picturesnap.domain.models;

public class Favorite {

    private final int id;
    private final int photographId;
    private final int clientId;

    public Favorite(int id, int photographId, int clientId) {
        this.id = id;
        this.photographId = photographId;
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public int getPhotographId() {
        return photographId;
    }

    public int getClientId() {
        return clientId;
    }
}
