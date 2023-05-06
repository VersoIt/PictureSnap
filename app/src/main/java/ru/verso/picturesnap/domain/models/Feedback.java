package ru.verso.picturesnap.domain.models;

public class Feedback {

    private String id;
    private String photographId;
    private String ownerName;
    private String text;
    private String imagePath;
    private String date;

    private int rating;

    public Feedback() {
    }

    public Feedback(String id, String photographId, String ownerName, String text, String imagePath, String date) {
        this.id = id;
        this.photographId = photographId;
        this.ownerName = ownerName;
        this.text = text;
        this.imagePath = imagePath;
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPhotographId(String clientId) {
        this.photographId = clientId;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public String getImagePath() {
        return imagePath;
    }

    public String getPhotographId() {
        return photographId;
    }

}
