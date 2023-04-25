package ru.verso.picturesnap.data.storage.room.dao;

import androidx.room.Dao;
import androidx.room.Query;

import ru.verso.picturesnap.data.storage.room.entity.BankCardPhotographEntity;

@Dao
public interface BankCardPhotographDAO {

    @Query("SELECT * FROM BankCardPhotographEntity WHERE photographId = :photographId")
    BankCardPhotographEntity getBankCardByPhotographId(int photographId);
}
