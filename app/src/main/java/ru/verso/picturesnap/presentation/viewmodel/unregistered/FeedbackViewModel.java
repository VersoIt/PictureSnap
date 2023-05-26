package ru.verso.picturesnap.presentation.viewmodel.unregistered;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.verso.picturesnap.domain.models.Feedback;
import ru.verso.picturesnap.domain.usecase.GetFeedbacksDataUseCase;

public class FeedbackViewModel extends ViewModel {

    private final GetFeedbacksDataUseCase getFeedbackDataUseCase;

    private String photographerId;

    public FeedbackViewModel(GetFeedbacksDataUseCase getFeedbackDataUseCase) {
        this.getFeedbackDataUseCase = getFeedbackDataUseCase;
    }

    public void putPhotographerId(String photographerId) {
        this.photographerId = photographerId;
    }

    public LiveData<List<Feedback>> getFeedbacksOfPhotographer() {
        return getFeedbackDataUseCase.getFeedbacksOfPhotographer(photographerId);
    }
}
