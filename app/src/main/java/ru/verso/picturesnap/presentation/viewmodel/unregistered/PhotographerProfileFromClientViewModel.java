package ru.verso.picturesnap.presentation.viewmodel.unregistered;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;

public class PhotographerProfileFromClientViewModel extends ViewModel {

    private final GetPhotographerDataUseCase getPhotographerDataUseCase;

    private String id;

    public PhotographerProfileFromClientViewModel(GetPhotographerDataUseCase getPhotographerDataUseCase) {
        this.getPhotographerDataUseCase = getPhotographerDataUseCase;
    }

    public LiveData<Photographer> getPhotographer() {
        return getPhotographerDataUseCase.getPhotographerById(id);
    }

    public void putId(String id) {
        this.id = id;
    }
}
