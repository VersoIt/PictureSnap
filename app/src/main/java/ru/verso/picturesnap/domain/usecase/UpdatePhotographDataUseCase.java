package ru.verso.picturesnap.domain.usecase;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.repository.PhotographRepository;

public class UpdatePhotographDataUseCase {

    private final PhotographRepository photographRepository;

    public UpdatePhotographDataUseCase(PhotographRepository photographRepository) {

        this.photographRepository = photographRepository;
    }

}


