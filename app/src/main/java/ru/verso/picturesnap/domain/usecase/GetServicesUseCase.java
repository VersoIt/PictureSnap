package ru.verso.picturesnap.domain.usecase;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.PhotographService;
import ru.verso.picturesnap.domain.repository.ServicesRepository;

public class GetServicesUseCase {

    private final ServicesRepository servicesRepository;

    public GetServicesUseCase(ServicesRepository servicesRepository) {
        this.servicesRepository = servicesRepository;
    }

    public LiveData<List<PhotographService>> getAllServices() {
        return servicesRepository.getAllServices();
    }
}
