package ru.verso.picturesnap.data.storage.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import ru.verso.picturesnap.data.storage.room.entity.FeedbackEntity;

@Dao
public interface FeedbackDAO {
    @Query("SELECT * FROM FeedbackEntity WHERE photographId = :photographId")
    LiveData<List<FeedbackEntity>> getFeedbacksByPhotographId(int photographId);
}