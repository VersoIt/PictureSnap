package ru.verso.picturesnap.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.data.storage.datasources.FeedbacksDataSource;
import ru.verso.picturesnap.domain.models.Feedback;
import ru.verso.picturesnap.domain.repository.FeedbackRepository;

public class FeedbacksRepositoryImpl implements FeedbackRepository {

    private final FeedbacksDataSource feedbacksDataSource;

    public FeedbacksRepositoryImpl(FeedbacksDataSource feedbacksDataSource) {
        this.feedbacksDataSource = feedbacksDataSource;
    }

    @Override
    public LiveData<List<Feedback>> getFeedbacksOf(String photographerId) {
        return feedbacksDataSource.getFeedbacksOf(photographerId);
    }

    @Override
    public void sendFeedback(Feedback feedback) {
        feedbacksDataSource.sendFeedback(feedback);
    }
}
