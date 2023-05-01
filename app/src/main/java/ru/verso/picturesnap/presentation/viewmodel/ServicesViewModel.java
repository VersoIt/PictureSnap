package ru.verso.picturesnap.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.verso.picturesnap.domain.models.PhotographService;
import ru.verso.picturesnap.domain.models.ServiceProvision;
import ru.verso.picturesnap.domain.usecase.GetPhotographDataUseCase;

public class ServicesViewModel extends ViewModel {

    private final GetPhotographDataUseCase getPhotographDataUseCase;

    private int photographId;

    public ServicesViewModel(GetPhotographDataUseCase getPhotographDataUseCase) {
        this.getPhotographDataUseCase = getPhotographDataUseCase;
    }

    public void putPhotographId(int photographId) {
        this.photographId = photographId;
    }

    public LiveData<List<PhotographService>> getPhotographServicesById() {
        return getPhotographDataUseCase.getPhotographServicesById(photographId);
    }
}
