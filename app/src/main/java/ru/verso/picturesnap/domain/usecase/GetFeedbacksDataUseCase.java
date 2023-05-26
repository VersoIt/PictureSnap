package ru.verso.picturesnap.domain.usecase;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.Feedback;
import ru.verso.picturesnap.domain.repository.FeedbackRepository;

public class GetFeedbacksDataUseCase {

    private final FeedbackRepository feedbackRepository;

    public GetFeedbacksDataUseCase(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public LiveData<List<Feedback>> getFeedbacksOfPhotographer(String photographerId) {
        return feedbackRepository.getFeedbacksOf(photographerId);
    }
}
