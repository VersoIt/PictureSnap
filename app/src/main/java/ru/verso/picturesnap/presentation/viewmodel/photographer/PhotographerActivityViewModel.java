package ru.verso.picturesnap.presentation.viewmodel.photographer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;

public class PhotographerActivityViewModel extends ViewModel {

    private final LiveData<Photographer> photographer;

    private final GetUserDataUseCase getUserDataUseCase;

    public PhotographerActivityViewModel(GetPhotographerDataUseCase getPhotographerDataUseCase, GetUserDataUseCase getUserDataUseCase) {
        photographer = getPhotographerDataUseCase.getPhotographerById(getUserDataUseCase.getUserId());
        this.getUserDataUseCase = getUserDataUseCase;
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

    public void signOut() {
        getUserDataUseCase.signOut();
    }
}
