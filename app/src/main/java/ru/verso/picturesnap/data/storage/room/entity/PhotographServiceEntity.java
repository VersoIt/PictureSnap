package ru.verso.picturesnap.data.storage.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import ru.verso.picturesnap.domain.models.PhotographService;

@Entity
public class PhotographServiceEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String iconPath;

    @ColumnInfo
    public int cost;

    @Ignore
    public PhotographServiceEntity(String name, String iconPath) {
        this.name = name;
        this.iconPath = iconPath;
    }

    public PhotographServiceEntity() {
    }
}
