package ru.verso.picturesnap.data.storage.datasources;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.Feedback;

public interface FeedbacksDataSource {

    LiveData<List<Feedback>> getFeedbacksOf(String photographerId);

    void sendFeedback(Feedback feedback);
}
