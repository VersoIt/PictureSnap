package ru.verso.picturesnap.presentation.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerPicturesUseCase;
import ru.verso.picturesnap.domain.usecase.SendPhotographerPicturesUseCase;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.ServicesViewModel;

public class ServicesViewModelFactory implements ViewModelProvider.Factory {

    private final GetPhotographerDataUseCase getPhotographerDataUseCase;

    private final GetPhotographerPicturesUseCase getPhotographerPicturesUseCase;

    private final SendPhotographerPicturesUseCase sendPhotographerPicturesUseCase;

    public ServicesViewModelFactory(GetPhotographerDataUseCase getPhotographerDataUseCase, GetPhotographerPicturesUseCase getPhotographerPicturesUseCase, SendPhotographerPicturesUseCase sendPhotographerPicturesUseCase) {
        this.getPhotographerDataUseCase = getPhotographerDataUseCase;
        this.getPhotographerPicturesUseCase = getPhotographerPicturesUseCase;
        this.sendPhotographerPicturesUseCase = sendPhotographerPicturesUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ServicesViewModel(getPhotographerDataUseCase, getPhotographerPicturesUseCase, sendPhotographerPicturesUseCase);
    }
}
