package ru.verso.picturesnap.data.storage.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FeedbackEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public int photographId;

    @ColumnInfo
    public int ownerId;

    @ColumnInfo
    public String text;

    @ColumnInfo
    public int rating;

    public FeedbackEntity() {}
}
