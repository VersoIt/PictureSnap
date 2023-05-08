package ru.verso.picturesnap.presentation.viewmodel.unregistered;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.verso.picturesnap.domain.models.Location;
import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.domain.usecase.GetPhotographDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdatePhotographDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;

public class ClientActivityViewModel extends ViewModel {

    private final MutableLiveData<Location> location;

    private final UpdateUserDataUseCase updateUserDataUseCase;

    private final GetUserDataUseCase getUserDataUseCase;


    public ClientActivityViewModel(UpdateUserDataUseCase updateUserDataUseCase,
                                   GetUserDataUseCase getUserDataUseCase) {

        this.updateUserDataUseCase = updateUserDataUseCase;
        this.getUserDataUseCase = getUserDataUseCase;

        location = new MutableLiveData<>(updateUserDataUseCase.getLocation());
    }

    public void setUserLocation(double latitude, double longitude) {
        updateUserDataUseCase.setLocation(latitude, longitude);
        this.location.setValue(new Location(latitude, longitude));
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