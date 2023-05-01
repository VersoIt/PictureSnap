package ru.verso.picturesnap.data.storage.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import ru.verso.picturesnap.data.storage.room.entity.PortfolioImageEntity;

@Dao
public interface PortfolioImageDAO {

    @Query("SELECT * FROM PortfolioImageEntity WHERE provisionId = :id")
    LiveData<List<PortfolioImageEntity>> getPortfolioImagesByServiceProvisionId(int id);
}
