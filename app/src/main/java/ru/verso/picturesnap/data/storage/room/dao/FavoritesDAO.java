package ru.verso.picturesnap.data.storage.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.verso.picturesnap.data.storage.room.entity.PhotographerEntity;

@Dao
public interface FavoritesDAO {

    @Query("SELECT * FROM PhotographerEntity")
    LiveData<List<PhotographerEntity>> getAllFavorites();

    @Insert
    void addFavorite(PhotographerEntity photographerEntity);

    @Delete
    void removeFavorite(PhotographerEntity photographer);
}
