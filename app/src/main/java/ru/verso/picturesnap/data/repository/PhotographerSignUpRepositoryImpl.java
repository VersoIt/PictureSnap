package ru.verso.picturesnap.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.data.storage.datasources.PhotographerSignUpDataSource;
import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.domain.repository.PhotographerSignUpRepository;
import ru.verso.picturesnap.domain.repository.SignUpFailureCallback;

public class PhotographerSignUpRepositoryImpl implements PhotographerSignUpRepository {

    private final PhotographerSignUpDataSource photographerSignUpDataSource;

    public PhotographerSignUpRepositoryImpl(PhotographerSignUpDataSource photographerSignUpDataSource) {
        this.photographerSignUpDataSource = photographerSignUpDataSource;
    }

    @Override
    public LiveData<Photographer> signUpPhotographer(Photographer photographer, List<PhotographerPresentationService> services, String password) {
        return photographerSignUpDataSource.signUpPhotographer(photographer, services, password);
    }

    @Override
    public LiveData<Photographer> signUpPhotographer(Photographer photographer, List<PhotographerPresentationService> services, String password, SignUpFailureCallback<Photographer> signUpCallback) {
        return photographerSignUpDataSource.signUpPhotographer(photographer, services, password, signUpCallback);
    }
}
