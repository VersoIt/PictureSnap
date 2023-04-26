package ru.verso.picturesnap.domain.usecase;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.repository.PhotographRepository;

public class OperationPhotographDataUseCase {

    private final PhotographRepository photographRepository;

    public OperationPhotographDataUseCase(PhotographRepository photographRepository) {

        this.photographRepository = photographRepository;
    }

    public LiveData<List<Photograph>> getPhotographsByLocation(String location) {
        return photographRepository.getPhotographsByLocation(location);
    }

}


