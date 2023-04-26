package ru.verso.picturesnap.domain.models;

public class Photograph {

    private String firstName;

    private String lastName;

    private String email;

    private int bankCardId;

    private String location;

    private String phoneNumber;

    private int experience;

    private String description;

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

        public Builder setEmail(String email) {
            photograph.email = email;
            return this;
        }

        public Builder setBankCardId(int id) {
            photograph.bankCardId = id;
            return this;
        }

        public Builder setLocation(String location) {
            photograph.location = location;
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