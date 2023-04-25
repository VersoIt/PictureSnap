package ru.verso.picturesnap.data.storage.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BankCardPhotographEntity {

    @PrimaryKey(autoGenerate = true)
    public int photographId;

    @ColumnInfo
    public String cardCode;

    public BankCardPhotographEntity() {
    }
}
