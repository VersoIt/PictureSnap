package ru.verso.picturesnap.presentation.viewmodel.unregistered;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.usecase.GetPhotographDataUseCase;

public class AboutPhotographFromClientViewModel extends ViewModel {

    private final GetPhotographDataUseCase getPhotographDataUseCase;

    private String photographId;

    public AboutPhotographFromClientViewModel(GetPhotographDataUseCase getPhotographDataUseCase) {
        this.getPhotographDataUseCase = getPhotographDataUseCase;
    }

    public void putPhotographId(String id) {
        this.photographId = id;
    }

    public LiveData<Photograph> getPhotograph() {
        return getPhotographDataUseCase.getPhotographById(photographId);
    }

}
