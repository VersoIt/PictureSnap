package ru.verso.picturesnap.presentation.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.presentation.viewmodel.photographer.PhotographerActivityViewModel;

public class PhotographerActivityViewModelFactory implements ViewModelProvider.Factory {

    private final GetPhotographerDataUseCase getPhotographerDataUseCase;

    private final GetUserDataUseCase getUserDataUseCase;

    public PhotographerActivityViewModelFactory(GetPhotographerDataUseCase getPhotographerDataUseCase, GetUserDataUseCase getUserDataUseCase) {
        this.getPhotographerDataUseCase = getPhotographerDataUseCase;
        this.getUserDataUseCase = getUserDataUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PhotographerActivityViewModel(getPhotographerDataUseCase, getUserDataUseCase);
    }
}
