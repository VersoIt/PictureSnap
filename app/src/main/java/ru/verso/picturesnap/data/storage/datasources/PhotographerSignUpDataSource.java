package ru.verso.picturesnap.data.storage.datasources;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.domain.repository.SignUpFailureCallback;

public interface PhotographerSignUpDataSource {

    LiveData<Photographer> signUpPhotographer(Photographer photographer, List<PhotographerPresentationService> services, String password);

    LiveData<Photographer> signUpPhotographer(Photographer photographer, List<PhotographerPresentationService> services, String password, SignUpFailureCallback<Photographer> signUpCallback);
}
