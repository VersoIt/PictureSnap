package ru.verso.picturesnap.presentation.activity.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.usecase.GetPhotographDataUseCase;

public class PhotographProfileViewModel extends ViewModel {

    private final GetPhotographDataUseCase getPhotographDataUseCase;

    private int id;

    public PhotographProfileViewModel(GetPhotographDataUseCase getPhotographDataUseCase) {
        this.getPhotographDataUseCase = getPhotographDataUseCase;
    }

    public LiveData<Photograph> getPhotograph() {
        return getPhotographDataUseCase.getPhotographById(id);
    }

    public void putId(int id) {
        this.id = id;
    }
}
