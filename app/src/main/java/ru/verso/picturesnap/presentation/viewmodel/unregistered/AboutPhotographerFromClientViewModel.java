package ru.verso.picturesnap.presentation.viewmodel.unregistered;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;

public class AboutPhotographerFromClientViewModel extends ViewModel {

    private final GetPhotographerDataUseCase getPhotographerDataUseCase;

    private String photographerId;

    public AboutPhotographerFromClientViewModel(GetPhotographerDataUseCase getPhotographerDataUseCase) {
        this.getPhotographerDataUseCase = getPhotographerDataUseCase;
    }

    public void putPhotographerId(String id) {
        this.photographerId = id;
    }

    public LiveData<Photographer> getPhotographer() {
        return getPhotographerDataUseCase.getPhotographerById(photographerId);
    }

}
