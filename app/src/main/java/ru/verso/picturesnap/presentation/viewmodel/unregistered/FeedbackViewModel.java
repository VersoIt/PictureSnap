package ru.verso.picturesnap.presentation.viewmodel.unregistered;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.verso.picturesnap.domain.models.Feedback;
import ru.verso.picturesnap.domain.usecase.GetFeedbackDataUseCase;

public class FeedbackViewModel extends ViewModel {

    private GetFeedbackDataUseCase getFeedbackDataUseCase;

    private String photographId;

    public FeedbackViewModel(GetFeedbackDataUseCase getFeedbackDataUseCase) {
        this.getFeedbackDataUseCase = getFeedbackDataUseCase;
    }

    public void putPhotographId(String photographId) {
        this.photographId = photographId;
    }

    public LiveData<List<Feedback>> getFeedbacksOfPhotograph() {
        return getFeedbackDataUseCase.getFeedbacksOfPhotograph(photographId);
    }
}
