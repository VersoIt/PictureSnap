package ru.verso.picturesnap.domain.usecase;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.domain.models.PhotographerService;
import ru.verso.picturesnap.domain.repository.PhotographerRepository;

public class GetPhotographerDataUseCase {

    private final PhotographerRepository repository;

    public GetPhotographerDataUseCase(PhotographerRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<PhotographerService>> getPhotographerServices() {
        return repository.getAllPhotographerServices();
    }

    public LiveData<List<Photographer>> getAllPhotographers() {
        return repository.getAllPhotographers();
    }

    public LiveData<Photographer> getPhotographerById(String id) {
        return repository.getPhotographerById(id);
    }

    public LiveData<List<PhotographerPresentationService>> getPhotographerServicesById(String photographerId) {
        return repository.getPhotographerServicesById(photographerId);
    }
}
