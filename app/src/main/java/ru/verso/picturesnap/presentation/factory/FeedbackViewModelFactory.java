package ru.verso.picturesnap.presentation.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.GetFeedbacksDataUseCase;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.FeedbackViewModel;

public class FeedbackViewModelFactory implements ViewModelProvider.Factory {

    private final GetFeedbacksDataUseCase getFeedbackDataUseCase;

    public FeedbackViewModelFactory(GetFeedbacksDataUseCase getFeedbackDataUseCase) {
        this.getFeedbackDataUseCase = getFeedbackDataUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FeedbackViewModel(getFeedbackDataUseCase);
    }
}
