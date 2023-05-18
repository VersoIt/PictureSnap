package ru.verso.picturesnap.presentation.viewmodel.unregistered;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.verso.picturesnap.domain.models.Location;
import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;
import ru.verso.picturesnap.presentation.activity.LocationKeeper;

public class ClientActivityViewModel extends ViewModel implements LocationKeeper {

    private final MutableLiveData<Location> location;

    private final UpdateUserDataUseCase updateUserDataUseCase;

    private final GetUserDataUseCase getUserDataUseCase;


    public ClientActivityViewModel(UpdateUserDataUseCase updateUserDataUseCase,
                                   GetUserDataUseCase getUserDataUseCase) {

        this.updateUserDataUseCase = updateUserDataUseCase;
        this.getUserDataUseCase = getUserDataUseCase;

        location = new MutableLiveData<>(updateUserDataUseCase.getLocation());
    }

    public boolean isFirst() {
        return getUserDataUseCase.isFirstCome();
    }

    public void setVisited() {
        updateUserDataUseCase.setVisited();
    }

    public RoleRepository.Role getCurrentRole() {
        return getUserDataUseCase.getRole();
    }

    @Override
    public void saveLocation(double latitude, double longitude) {
        updateUserDataUseCase.setLocation(latitude, longitude);
        this.location.setValue(new Location(latitude, longitude));
    }
}