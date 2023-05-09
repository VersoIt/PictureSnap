package ru.verso.picturesnap.data.storage.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BankCardPhotographerEntity {

    @PrimaryKey(autoGenerate = true)
    public int photographerId;

    @ColumnInfo
    public String cardCode;

    public BankCardPhotographerEntity() {
    }
}
