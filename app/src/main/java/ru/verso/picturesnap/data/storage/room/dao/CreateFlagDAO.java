package ru.verso.picturesnap.data.storage.room.dao;

import androidx.room.Dao;
import androidx.room.PrimaryKey;

@Dao
public interface CreateFlagDAO {

    @PrimaryKey(autoGenerate = true)
    int statusCode();
}
