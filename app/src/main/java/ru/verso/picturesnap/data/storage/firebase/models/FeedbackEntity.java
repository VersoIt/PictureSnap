package ru.verso.picturesnap.data.storage.firebase.models;

import ru.verso.picturesnap.domain.models.Feedback;

public class FeedbackEntity {

    private String id;

    private String photographerId;

    private String ownerId;

    private String text;

    private String date;

    private int rating;

    private FeedbackEntity() {}

    public FeedbackEntity(String id, String photographerId, String ownerId, String text, int rating, String date) {
        this.id = id;
        this.photographerId = photographerId;
        this.ownerId = ownerId;
        this.text = text;
        this.rating = rating;
        this.date = date;
    }

    public Feedback mapToDomain() {
        Feedback feedback = new Feedback();

        feedback.setId(id);
        feedback.setText(text);
        feedback.setRating(rating);
        feedback.setPhotographerId(photographerId);
        feedback.setDate(date);

        return feedback;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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
