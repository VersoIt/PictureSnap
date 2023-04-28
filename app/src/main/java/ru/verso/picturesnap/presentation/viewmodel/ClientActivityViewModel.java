package ru.verso.picturesnap.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.domain.usecase.GetPhotographDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdatePhotographDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;

public class ClientActivityViewModel extends ViewModel {

    private final MutableLiveData<String> location;

    private final UpdateUserDataUseCase updateUserDataUseCase;

    private final LiveData<List<Photograph>> photographsByLocation;

    private final GetPhotographDataUseCase getPhotographDataUseCase;

    private final GetUserDataUseCase getUserDataUseCase;

    private final UpdatePhotographDataUseCase updatePhotographDataUseCase;

    public ClientActivityViewModel(UpdateUserDataUseCase updateUserDataUseCase,
                                   GetUserDataUseCase getUserDataUseCase,
                                   UpdatePhotographDataUseCase updatePhotographDataUseCase,
                                   GetPhotographDataUseCase getPhotographDataUseCase) {

        this.updateUserDataUseCase = updateUserDataUseCase;
        this.getUserDataUseCase = getUserDataUseCase;
        this.getPhotographDataUseCase = getPhotographDataUseCase;
        this.updatePhotographDataUseCase = updatePhotographDataUseCase;

        location = new MutableLiveData<>(updateUserDataUseCase.getLocation());
        photographsByLocation = getPhotographDataUseCase.getPhotographsByLocation(location.getValue());
    }

    public void setLocation(String location) {
        updateUserDataUseCase.setLocation(location);
        this.location.setValue(location);
    }

    public LiveData<List<Photograph>> getPhotographsByLocation() {
        return photographsByLocation;
    }

    public boolean isFirst() {
        return getUserDataUseCase.isFirstCome();
    }

    public void setVisited() {
        updateUserDataUseCase.setVisited();
    }

    public RoleRepository.Role getCurrentRole() {
        return getUserDataUseCase.getCurrentRole();
    }
}