package ru.verso.picturesnap.domain.models;

public class Photographer {

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private double latitude;

    private double longitude;

    private String phoneNumber;

    private float rating;

    private int experience;

    private String description;

    private String avatarPath;

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getExperience() {
        return experience;
    }

    public String getDescription() {
        return description;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public float getRating() {
        return rating;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Photographer() {
    }

    public static class Builder {

        private final Photographer photographer;

        public Builder setName(String firstName, String lastName) {
            photographer.firstName = firstName;
            photographer.lastName = lastName;
            return this;
        }

        public Builder() {
            photographer = new Photographer();
        }

        public Photographer create() {
            return photographer;
        }

        public Builder setAvatarPath(String avatarPath) {
            photographer.avatarPath = avatarPath;
            return this;
        }

        public Builder setEmail(String email) {
            photographer.email = email;
            return this;
        }

        public Builder setRating(float rating) {
            photographer.rating = rating;
            return this;
        }

        public Builder setId(String id) {
            photographer.id = id;
            return this;
        }

        public Builder setLocation(double latitude, double longitude) {
            photographer.latitude = latitude;
            photographer.longitude = longitude;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            photographer.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setExperience(int experience) {
            photographer.experience = experience;
            return this;
        }

        public Builder setDescription(String description) {
            photographer.description = description;
            return this;
        }
    }
}