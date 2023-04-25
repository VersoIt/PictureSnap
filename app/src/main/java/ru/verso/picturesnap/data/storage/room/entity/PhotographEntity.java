package ru.verso.picturesnap.data.storage.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
    public String experience;

    @ColumnInfo
    public String description;

    public PhotographEntity() {
    }
}
