package ru.verso.picturesnap.data.storage.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.verso.picturesnap.data.storage.room.entity.PhotographEntity;

@Dao
public interface FavoritesDAO {

    @Query("SELECT * FROM PhotographEntity")
    LiveData<List<PhotographEntity>> getAllFavorites();

    @Insert
    void addFavorite(PhotographEntity photographEntity);

    @Delete
    void removeFavorite(PhotographEntity photograph);
}
