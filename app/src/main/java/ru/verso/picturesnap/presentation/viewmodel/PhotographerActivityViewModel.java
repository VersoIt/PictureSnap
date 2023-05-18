package ru.verso.picturesnap.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;

public class PhotographerActivityViewModel extends ViewModel {

    private final LiveData<Photographer> photographer;

    public PhotographerActivityViewModel(GetPhotographerDataUseCase getPhotographerDataUseCase, GetUserDataUseCase getUserDataUseCase) {
        photographer = getPhotographerDataUseCase.getPhotographerById(getUserDataUseCase.getUserId());
    }

    public LiveData<String> getPhotographerName() {
        return Transformations.map(photographer, p -> String.format("%s %s", p.getFirstName(), p.getLastName()));
    }

    public LiveData<String> getPhotographerEmail() {
        return Transformations.map(photographer, Photographer::getEmail);
    }

    public LiveData<String> getPhotographerAvatar() {
        return Transformations.map(photographer, Photographer::getAvatarPath);
    }
}
