package ru.verso.picturesnap.data.storage.room.firebase.models;

import java.util.Date;

import ru.verso.picturesnap.domain.models.Feedback;

public class FeedbackEntity {

    private String id;
    private String photographerId;
    private String ownerName;
    private String text;
    private Date date;

    private int rating;

    private FeedbackEntity() {
    }

    public FeedbackEntity(String id, String photographerId, String ownerName, String text, Date date) {
        this.id = id;
        this.photographerId = photographerId;
        this.ownerName = ownerName;
        this.text = text;
        this.date = date;
    }

    public Feedback mapToDomain() {
        Feedback feedback = new Feedback();

        feedback.setId(id);
        feedback.setText(text);
        feedback.setRating(rating);
        feedback.setPhotographerId(photographerId);
        feedback.setDate(date);
        feedback.setOwnerName(ownerName);

        return feedback;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotographerId() {
        return photographerId;
    }

    public void setPhotographerId(String photographerId) {
        this.photographerId = photographerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
