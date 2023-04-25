package ru.verso.picturesnap.data.storage.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ClientEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public ClientEntity() {}
}
