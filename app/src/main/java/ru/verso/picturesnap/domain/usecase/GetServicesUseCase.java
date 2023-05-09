package ru.verso.picturesnap.domain.usecase;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.PhotographerService;
import ru.verso.picturesnap.domain.repository.ServicesRepository;

public class GetServicesUseCase {

    private final ServicesRepository servicesRepository;

    public GetServicesUseCase(ServicesRepository servicesRepository) {
        this.servicesRepository = servicesRepository;
    }

    public LiveData<List<PhotographerService>> getAllServices() {
        return servicesRepository.getAllServices();
    }
}
