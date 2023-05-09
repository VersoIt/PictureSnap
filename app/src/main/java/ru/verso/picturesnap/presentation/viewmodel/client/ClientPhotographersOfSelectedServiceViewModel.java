package ru.verso.picturesnap.presentation.viewmodel.client;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.models.PhotographerService;
import ru.verso.picturesnap.domain.usecase.GetPhotographerServiceByIdUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographersByServiceIdUseCase;

public class ClientPhotographersOfSelectedServiceViewModel extends ViewModel {

    private final GetPhotographersByServiceIdUseCase getPhotographersByServiceIdUseCase;

    private final GetPhotographerServiceByIdUseCase getPhotographerServiceByIdUseCase;

    private String serviceId;

    public ClientPhotographersOfSelectedServiceViewModel(GetPhotographersByServiceIdUseCase getPhotographersByServiceIdUseCase, GetPhotographerServiceByIdUseCase getPhotographerServiceByIdUseCase) {
        this.getPhotographersByServiceIdUseCase = getPhotographersByServiceIdUseCase;
        this.getPhotographerServiceByIdUseCase = getPhotographerServiceByIdUseCase;
    }

    public void putServiceId(String id) {
        this.serviceId = id;
    }

    public LiveData<List<Photographer>> getPhotographersByServiceId() {
        return getPhotographersByServiceIdUseCase.getPhotographers(serviceId);
    }

    public LiveData<PhotographerService> getService() {
        return getPhotographerServiceByIdUseCase.get(serviceId);
    }
}
