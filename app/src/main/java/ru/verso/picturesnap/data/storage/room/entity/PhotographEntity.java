package ru.verso.picturesnap.data.storage.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import ru.verso.picturesnap.domain.models.Photograph;

@Entity
public class PhotographEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public String firstName;

    @ColumnInfo
    public String lastName;

    @ColumnInfo
    public String email;

    @ColumnInfo
    public int bankCardId;

    @ColumnInfo
    public String location;

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

    public PhotographEntity() {
    }

    public Photograph mapToDomain() {
        Photograph.Builder builder = new Photograph.Builder();

        return builder.setName(firstName, lastName)
                .setDescription(description)
                .setEmail(email)
                .setExperience(experience)
                .setBankCardId(id)
                .setPhoneNumber(phoneNumber)
                .setLocation(location)
                .setId(id)
                .setRating(rating)
                .setAvatarPath(avatarPath).create();
    }

    public static class Builder {

        private final PhotographEntity photograph;

        public Builder setId(int id) {
            photograph.id = id;
            return this;
        }

        public Builder setName(String firstName, String lastName) {
            photograph.firstName = firstName;
            photograph.lastName = lastName;
            return this;
        }

        public Builder() {
            photograph = new PhotographEntity();
        }

        public PhotographEntity create() {
            return photograph;
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
