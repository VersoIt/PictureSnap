package ru.verso.picturesnap.data.storage.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.verso.picturesnap.data.storage.room.entity.WorkingDayEntity;

@Dao
public interface WorkingDaysDAO {
    @Query("SELECT * FROM WorkingDayEntity WHERE photographId = :photographId")
    LiveData<List<WorkingDayEntity>> getWorkingDaysByPhotograph(int photographId);

    @Insert
    void addWorkingDay(WorkingDayEntity workingDay);
}
