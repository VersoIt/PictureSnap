package ru.verso.picturesnap.domain.usecase;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.domain.repository.PhotographerSignUpRepository;
import ru.verso.picturesnap.domain.repository.SignUpFailureCallback;

public class SignUpNewPhotographerUseCase {

    private final PhotographerSignUpRepository photographerRepository;

    public SignUpNewPhotographerUseCase(PhotographerSignUpRepository photographerRepository) {
        this.photographerRepository = photographerRepository;
    }

    public LiveData<Photographer> signUpPhotographer(Photographer photographer, List<PhotographerPresentationService> services, String password, SignUpFailureCallback<Photographer> signUpCallback) {
        return photographerRepository.signUpPhotographer(photographer, services, password, signUpCallback);
    }
}
