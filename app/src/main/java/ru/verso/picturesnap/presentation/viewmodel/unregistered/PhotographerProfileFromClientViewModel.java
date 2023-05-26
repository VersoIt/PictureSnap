package ru.verso.picturesnap.presentation.viewmodel.unregistered;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;

public class PhotographerProfileFromClientViewModel extends ViewModel {

    private final GetPhotographerDataUseCase getPhotographerDataUseCase;

    private String id;

    private final GetUserDataUseCase getUserDataUseCase;

    public PhotographerProfileFromClientViewModel(GetPhotographerDataUseCase getPhotographerDataUseCase, GetUserDataUseCase getUserDataUseCase) {
        this.getPhotographerDataUseCase = getPhotographerDataUseCase;
        this.getUserDataUseCase = getUserDataUseCase;
    }

    public LiveData<Photographer> getPhotographer() {
        return getPhotographerDataUseCase.getPhotographerById(id);
    }

    public String getClientId() {
        return getUserDataUseCase.getUserId();
    }

    public void putId(String id) {
        this.id = id;
    }
}
