package ru.verso.picturesnap.domain.usecase;

import androidx.lifecycle.LiveData;

import ru.verso.picturesnap.domain.models.PhotographerService;
import ru.verso.picturesnap.domain.repository.PhotographerServiceRepository;

public class GetPhotographerServiceByIdUseCase {

    private final PhotographerServiceRepository photographerServiceRepository;

    public GetPhotographerServiceByIdUseCase(PhotographerServiceRepository photographerServiceRepository) {
        this.photographerServiceRepository = photographerServiceRepository;
    }

    public LiveData<PhotographerService> get(String id) {
        return photographerServiceRepository.getServiceById(id);
    }
}
