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

    @Query("SELECT PhotographServiceEntity.id, PhotographServiceEntity.name, PhotographServiceEntity.iconPath, ServiceProvisionEntity.cost " +
            "FROM PhotographServiceEntity " +
            "JOIN ServiceProvisionEntity " +
            "ON ServiceProvisionEntity.serviceId = PhotographServiceEntity.id " +
            "WHERE ServiceProvisionEntity.photographId = :photographId")
    LiveData<List<PhotographServiceEntity>> getServicesOfPhotograph(int photographId);
}
