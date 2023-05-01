package ru.verso.picturesnap.data.storage.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import ru.verso.picturesnap.data.storage.room.entity.ServiceProvisionEntity;

@Dao
public interface ServiceProvisionEntityDAO {

    @Insert
    void addServiceProvisionEntity(ServiceProvisionEntity entity);
}