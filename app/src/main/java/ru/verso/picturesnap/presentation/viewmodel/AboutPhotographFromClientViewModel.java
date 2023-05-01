package ru.verso.picturesnap.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.usecase.GetPhotographDataUseCase;

public class AboutPhotographFromClientViewModel extends ViewModel {

    private final GetPhotographDataUseCase getPhotographDataUseCase;

    private int photographId;

    public AboutPhotographFromClientViewModel(GetPhotographDataUseCase getPhotographDataUseCase) {
        this.getPhotographDataUseCase = getPhotographDataUseCase;
    }

    public void putPhotographId(int id) {
        this.photographId = id;
    }

    public LiveData<Photograph> getPhotograph() {
        return getPhotographDataUseCase.getPhotographById(photographId);
    }

}
