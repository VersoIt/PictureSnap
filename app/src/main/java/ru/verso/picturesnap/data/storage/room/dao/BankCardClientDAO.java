package ru.verso.picturesnap.data.storage.room.dao;

import androidx.room.Dao;
import androidx.room.Query;

import ru.verso.picturesnap.data.storage.room.entity.BankCardClientEntity;

@Dao
public interface BankCardClientDAO {
    @Query("SELECT * FROM BankCardClientEntity WHERE clientId = :clientId")
    BankCardClientEntity getBankCardByClientId(int clientId);
}
