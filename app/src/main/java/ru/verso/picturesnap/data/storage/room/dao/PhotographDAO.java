package ru.verso.picturesnap.data.storage.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.verso.picturesnap.data.storage.room.entity.PhotographEntity;

@Dao
public interface PhotographDAO {

    @Query("SELECT * FROM PhotographEntity WHERE firstName + lastName LIKE :name")
    LiveData<List<PhotographEntity>> getPhotographByName(String name);

    @Query("SELECT * FROM PhotographEntity WHERE id = :id")
    LiveData<PhotographEntity> getPhotographById(int id);

    @Query("SELECT * FROM PhotographEntity")
    LiveData<List<PhotographEntity>> getAllPhotographs();

    @Query("SELECT * FROM PhotographEntity WHERE firstName LIKE :firstName")
    LiveData<List<PhotographEntity>> getPhotographsContainsFirstName(String firstName);

    @Query("SELECT * FROM PhotographEntity WHERE lastName LIKE :lastName")
    LiveData<List<PhotographEntity>> getPhotographsContainsLastName(String lastName);

    @Insert
    void addPhotograph(PhotographEntity photograph);
}