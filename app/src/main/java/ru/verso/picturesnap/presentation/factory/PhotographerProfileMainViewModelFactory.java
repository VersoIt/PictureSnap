package ru.verso.picturesnap.presentation.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.presentation.viewmodel.photographer.PhotographerProfileMainViewModel;

public class PhotographerProfileMainViewModelFactory implements ViewModelProvider.Factory {

    private final GetPhotographerDataUseCase getPhotographerDataUseCase;

    private final GetUserDataUseCase getUserDataUseCase;

    public PhotographerProfileMainViewModelFactory(GetPhotographerDataUseCase getPhotographerDataUseCase, GetUserDataUseCase getUserDataUseCase) {
        this.getPhotographerDataUseCase = getPhotographerDataUseCase;
        this.getUserDataUseCase = getUserDataUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass, @NonNull CreationExtras extras) {
        return (T) new PhotographerProfileMainViewModel(getPhotographerDataUseCase, getUserDataUseCase);
    }
}
