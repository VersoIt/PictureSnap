package ru.verso.picturesnap.presentation.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.AboutPhotographerFromClientViewModel;

public class AboutPhotographerFromClientViewModelFactory implements ViewModelProvider.Factory {

    private final GetPhotographerDataUseCase getPhotographerDataUseCase;

    public AboutPhotographerFromClientViewModelFactory(GetPhotographerDataUseCase getPhotographerDataUseCase) {
        this.getPhotographerDataUseCase = getPhotographerDataUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AboutPhotographerFromClientViewModel(getPhotographerDataUseCase);
    }
}
