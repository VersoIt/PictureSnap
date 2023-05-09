package ru.verso.picturesnap.presentation.viewmodel.unregistered;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;

public class ServicesViewModel extends ViewModel {

    private final GetPhotographerDataUseCase getPhotographerDataUseCase;

    private String photographerId;

    public ServicesViewModel(GetPhotographerDataUseCase getPhotographerDataUseCase) {
        this.getPhotographerDataUseCase = getPhotographerDataUseCase;
    }

    public void putPhotographerId(String photographerId) {
        this.photographerId = photographerId;
    }

    public LiveData<List<PhotographerPresentationService>> getPhotographerServices() {
        return getPhotographerDataUseCase.getPhotographerServicesById(photographerId);
    }
}
