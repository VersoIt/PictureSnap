package ru.verso.picturesnap.data.storage.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import ru.verso.picturesnap.domain.models.Photographer;

@Entity
public class PhotographerEntity {

    @NonNull
    @PrimaryKey
    public String id;

    @ColumnInfo
    public String firstName;

    @ColumnInfo
    public String lastName;

    @ColumnInfo
    public String email;

    @ColumnInfo
    public double latitude;

    @ColumnInfo
    public double longitude;

    @ColumnInfo
    public String phoneNumber;

    @ColumnInfo
    public int experience;

    @ColumnInfo
    public float rating;

    @ColumnInfo
    public String description;

    @ColumnInfo
    public String avatarPath;

    public PhotographerEntity() {
    }

    public Photographer mapToDomain() {
        Photographer.Builder builder = new Photographer.Builder();

        return builder.setName(firstName, lastName)
                .setDescription(description)
                .setEmail(email)
                .setExperience(experience)
                .setPhoneNumber(phoneNumber)
                .setLocation(latitude, longitude)
                .setId(id)
                .setRating(rating)
                .setAvatarPath(avatarPath)
                .create();
    }

    public static class Builder {

        private final PhotographerEntity photographer;

        public Builder setId(String id) {
            photographer.id = id;
            return this;
        }

        public Builder setName(String firstName, String lastName) {
            photographer.firstName = firstName;
            photographer.lastName = lastName;
            return this;
        }

        public Builder() {
            photographer = new PhotographerEntity();
        }

        public PhotographerEntity create() {
            return photographer;
        }

        public Builder setEmail(String email) {
            photographer.email = email;
            return this;
        }

        public Builder setAvatarPath(String avatarPath) {
            photographer.avatarPath = avatarPath;
            return this;
        }

        public Builder setLocation(double latitude, double longitude) {
            photographer.latitude = latitude;
            photographer.longitude = longitude;
            return this;
        }

        public Builder setRating(float rating) {
            photographer.rating = rating;
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