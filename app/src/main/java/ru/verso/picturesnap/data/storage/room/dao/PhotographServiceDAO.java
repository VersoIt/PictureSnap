package ru.verso.picturesnap.data.storage.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.verso.picturesnap.data.storage.room.entity.PhotographServiceEntity;

@Dao
public interface PhotographServiceDAO {
    @Query("SELECT * FROM PhotographServiceEntity")
    LiveData<List<PhotographServiceEntity>> getAllServices();

    @Insert
    void addNewService(PhotographServiceEntity service);
}
