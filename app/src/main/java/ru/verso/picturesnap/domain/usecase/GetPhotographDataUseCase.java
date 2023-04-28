package ru.verso.picturesnap.domain.usecase;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.models.PhotographService;
import ru.verso.picturesnap.domain.repository.PhotographRepository;

public class GetPhotographDataUseCase {

    private final PhotographRepository repository;

    public GetPhotographDataUseCase(PhotographRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<PhotographService>> getPhotographServices() {
        return repository.getAllPhotographServices();
    }

    public LiveData<List<Photograph>> getAllPhotographs() {
        return repository.getAllPhotographs();
    }

    public LiveData<Photograph> getPhotographById(int id) {
        return repository.getPhotographById(id);
    }
}
