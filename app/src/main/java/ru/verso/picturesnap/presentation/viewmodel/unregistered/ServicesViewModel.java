package ru.verso.picturesnap.presentation.viewmodel.unregistered;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.verso.picturesnap.domain.models.PhotographPresentationService;
import ru.verso.picturesnap.domain.usecase.GetPhotographDataUseCase;

public class ServicesViewModel extends ViewModel {

    private final GetPhotographDataUseCase getPhotographDataUseCase;

    private String photographId;

    public ServicesViewModel(GetPhotographDataUseCase getPhotographDataUseCase) {
        this.getPhotographDataUseCase = getPhotographDataUseCase;
    }

    public void putPhotographId(String photographId) {
        this.photographId = photographId;
    }

    public LiveData<List<PhotographPresentationService>> getPhotographServices() {
        return getPhotographDataUseCase.getPhotographServicesById(photographId);
    }
}
