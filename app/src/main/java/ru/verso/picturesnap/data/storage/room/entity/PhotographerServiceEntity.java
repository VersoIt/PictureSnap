package ru.verso.picturesnap.data.storage.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class PhotographerServiceEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String iconPath;

    @ColumnInfo
    public int cost;

    @Ignore
    public PhotographerServiceEntity(String name, String iconPath) {
        this.name = name;
        this.iconPath = iconPath;
    }

    public PhotographerServiceEntity() {
    }
}
