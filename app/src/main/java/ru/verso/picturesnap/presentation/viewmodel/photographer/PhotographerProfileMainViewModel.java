package ru.verso.picturesnap.presentation.viewmodel.photographer;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;

public class PhotographerProfileMainViewModel extends ViewModel {

    private final LiveData<Photographer> photographer;

    private final GetUserDataUseCase getUserDataUseCase;

    private final GetPhotographerDataUseCase getPhotographerDataUseCase;

    private final String photographerId;

    public PhotographerProfileMainViewModel(GetPhotographerDataUseCase getPhotographerDataUseCase, GetUserDataUseCase getUserDataUseCase) {
        this.getPhotographerDataUseCase = getPhotographerDataUseCase;
        this.getUserDataUseCase = getUserDataUseCase;
        photographerId = getUserDataUseCase.getUserId();
        photographer = getPhotographerDataUseCase.getPhotographerById(photographerId);
    }

    public LiveData<Photographer> getPhotographer() {
        return photographer;
    }

    public void signOut() {
        getUserDataUseCase.signOut();
    }

    public void loadNewImage(Uri uri) {
        getPhotographerDataUseCase.updatePhotographerAvatar(photographerId, uri);
    }
}
