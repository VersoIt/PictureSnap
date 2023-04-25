package ru.verso.picturesnap.data.storage.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CreateFlag {

    @PrimaryKey
    @ColumnInfo
    public int statusCode;
}
