package ru.verso.picturesnap.data.storage.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.verso.picturesnap.data.storage.room.entity.ServiceProvisionEntity;
import ru.verso.picturesnap.domain.models.ServiceProvision;

@Dao
public interface ServiceProvisionEntityDAO {

    @Insert
    void addServiceProvisionEntity(ServiceProvisionEntity entity);

    @Query("SELECT * FROM ServiceProvisionEntity WHERE photographId = :photographId")
    LiveData<List<ServiceProvisionEntity>> getPhotographServiceProvisions(int photographId);
}