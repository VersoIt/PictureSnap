package ru.verso.picturesnap.data.storage.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import ru.verso.picturesnap.data.storage.room.entity.WorkingDaysEntity;

@Dao
public interface WorkingDaysDAO {
    @Query("SELECT * FROM WorkingDaysEntity WHERE photographId = :photographId")
    LiveData<List<WorkingDaysEntity>> getWorkingDaysByPhotograph(int photographId);
}
