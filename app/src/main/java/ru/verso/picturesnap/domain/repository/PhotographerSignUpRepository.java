package ru.verso.picturesnap.domain.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;

public interface PhotographerSignUpRepository {

    LiveData<Photographer> signUpPhotographer(Photographer photographer, List<PhotographerPresentationService> services, String password, SignUpFailureCallback<Photographer> signUpCallback);

    LiveData<Photographer> signUpPhotographer(Photographer photographer, List<PhotographerPresentationService> services, String password);
}
