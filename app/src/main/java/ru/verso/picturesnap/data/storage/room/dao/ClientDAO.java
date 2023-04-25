package ru.verso.picturesnap.data.storage.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;
import ru.verso.picturesnap.data.storage.room.entity.ClientEntity;

@Dao
public interface ClientDAO {

    @Query("SELECT * FROM clientEntity WHERE id = :id")
    ClientEntity getClientById(int id);

    @Query("SELECT * FROM cliententity")
    LiveData<List<ClientEntity>> getAllClients();
}
