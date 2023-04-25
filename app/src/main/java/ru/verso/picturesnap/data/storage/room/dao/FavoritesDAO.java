package ru.verso.picturesnap.data.storage.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import ru.verso.picturesnap.data.storage.room.entity.FavoriteEntity;
import ru.verso.picturesnap.data.storage.room.entity.PhotographEntity;

@Dao
public interface FavoritesDAO {
    @Query("SELECT * FROM FavoriteEntity WHERE clientId = :clientId")
    LiveData<List<FavoriteEntity>> getFavoritesByClientId(int clientId);
}
