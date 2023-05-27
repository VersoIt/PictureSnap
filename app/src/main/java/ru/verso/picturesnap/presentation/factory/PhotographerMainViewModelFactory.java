package ru.verso.picturesnap.presentation.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.GetPhotographerRecordsUseCase.GetPhotographerRecordsUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.presentation.viewmodel.photographer.PhotographerMainViewModel;

public class PhotographerMainViewModelFactory implements ViewModelProvider.Factory {

    private final GetPhotographerRecordsUseCase getPhotographerRecordsUseCase;

    private final GetUserDataUseCase getUserDataUseCase;

    public PhotographerMainViewModelFactory(GetPhotographerRecordsUseCase getPhotographerRecordsUseCase, GetUserDataUseCase getUserDataUseCase) {
        this.getPhotographerRecordsUseCase = getPhotographerRecordsUseCase;
        this.getUserDataUseCase = getUserDataUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PhotographerMainViewModel(getPhotographerRecordsUseCase, getUserDataUseCase);
    }
}
