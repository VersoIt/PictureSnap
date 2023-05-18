package ru.verso.picturesnap.domain.models;

import java.util.Date;

public class Feedback {

    private String id;
    private String photographerId;
    private String ownerName;
    private String text;
    private Date date;

    private int rating;

    public Feedback() {
    }

    public Feedback(String id, String photographerId, String ownerName, String text, Date date) {
        this.id = id;
        this.photographerId = photographerId;
        this.ownerName = ownerName;
        this.text = text;
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPhotographerId(String clientId) {
        this.photographerId = clientId;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getText() {
        return text;
    }

    public String getPhotographerId() {
        return photographerId;
    }

}
