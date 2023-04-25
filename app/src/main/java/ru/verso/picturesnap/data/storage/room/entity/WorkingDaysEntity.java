package ru.verso.picturesnap.data.storage.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WorkingDaysEntity {

    @PrimaryKey (autoGenerate = true)
    public int id;

    @ColumnInfo
    public int photographId;

    @ColumnInfo
    public int dayId;

    @ColumnInfo
    public int secondsFrom;

    @ColumnInfo
    public int secondsTo;

    public WorkingDaysEntity() {
    }
}
