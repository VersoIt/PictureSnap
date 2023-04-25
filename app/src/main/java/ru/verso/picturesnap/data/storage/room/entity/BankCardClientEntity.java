package ru.verso.picturesnap.data.storage.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BankCardClientEntity {

    @PrimaryKey(autoGenerate = true)
    public int clientId;

    @ColumnInfo
    public String cardCode;

    public BankCardClientEntity() {
    }
}
