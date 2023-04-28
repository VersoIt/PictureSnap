package ru.verso.picturesnap.data.storage.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoriteEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public int clientId;

    @ColumnInfo
    public int photographId;

    public FavoriteEntity() {
    }
}