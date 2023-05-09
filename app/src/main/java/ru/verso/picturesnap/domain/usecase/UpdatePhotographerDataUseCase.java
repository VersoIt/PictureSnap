package ru.verso.picturesnap.domain.usecase;

import ru.verso.picturesnap.domain.repository.PhotographerRepository;

public class UpdatePhotographerDataUseCase {

    private final PhotographerRepository photographerRepository;

    public UpdatePhotographerDataUseCase(PhotographerRepository photographerRepository) {

        this.photographerRepository = photographerRepository;
    }

}


