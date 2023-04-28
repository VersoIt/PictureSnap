package ru.verso.picturesnap.domain.models;

public class Photograph {

    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private int bankCardId;

    private double latitude;

    private double longitude;

    private String phoneNumber;

    private float rating;

    private int experience;

    private String description;

    private String avatarPath;

    public int getId() {
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

    public void setLocation() {

    }

    public int getBankCardId() {
        return bankCardId;
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

    public static class Builder {

        private final Photograph photograph;

        public Builder setName(String firstName, String lastName) {
            photograph.firstName = firstName;
            photograph.lastName = lastName;
            return this;
        }

        public Builder() {
            photograph = new Photograph();
        }

        public Photograph create() {
            return photograph;
        }

        public Builder setAvatarPath(String avatarPath) {
            photograph.avatarPath = avatarPath;
            return this;
        }

        public Builder setEmail(String email) {
            photograph.email = email;
            return this;
        }

        public Builder setRating(float rating) {
            photograph.rating = rating;
            return this;
        }

        public Builder setBankCardId(int id) {
            photograph.bankCardId = id;
            return this;
        }

        public Builder setId(int id) {
            photograph.id = id;
            return this;
        }

        public Builder setLocation(double latitude, double longitude) {
            photograph.latitude = latitude;
            photograph.longitude = longitude;
            return this;
        }

        public Builder setPhoneNumber(String phoneNumber) {
            photograph.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setExperience(int experience) {
            photograph.experience = experience;
            return this;
        }

        public Builder setDescription(String description) {
            photograph.description = description;
            return this;
        }
    }
}