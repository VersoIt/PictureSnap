package ru.verso.picturesnap.presentation.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.GetPhotographDataUseCase;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.ServicesViewModel;

public class ServicesViewModelFactory implements ViewModelProvider.Factory {

    private final GetPhotographDataUseCase getPhotographDataUseCase;

    public ServicesViewModelFactory(GetPhotographDataUseCase getPhotographDataUseCase) {
        this.getPhotographDataUseCase = getPhotographDataUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ServicesViewModel(getPhotographDataUseCase);
    }
}
