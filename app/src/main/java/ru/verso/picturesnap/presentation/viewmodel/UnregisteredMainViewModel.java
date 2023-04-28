package ru.verso.picturesnap.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.models.PhotographService;
import ru.verso.picturesnap.domain.usecase.GetPhotographDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;

public class UnregisteredMainViewModel extends ViewModel {

    private final LiveData<List<PhotographService>> services;

    private final MutableLiveData<String> location;

    private final GetUserDataUseCase getUserDataUseCase;

    private final LiveData<List<Photograph>> photographsInCity;

    private final UpdateUserDataUseCase updateUserDataUseCase;

    public UnregisteredMainViewModel(GetPhotographDataUseCase getPhotographDataUseCase,
                                     GetUserDataUseCase getUserDataUseCase,
                                     UpdateUserDataUseCase updateUserDataUseCase) {
        services = getPhotographDataUseCase.getPhotographServices();
        this.getUserDataUseCase = getUserDataUseCase;
        photographsInCity = getPhotographDataUseCase.getPhotographsByLocation(getUserDataUseCase.getLocation());

        location = new MutableLiveData<>(getUserDataUseCase.getLocation());
        this.updateUserDataUseCase = updateUserDataUseCase;
    }

    public void setLocation(String location) {
        updateUserDataUseCase.setLocation(location);
    }

    public LiveData<String> getLocation() {
        this.location.setValue(getUserDataUseCase.getLocation());
        return location;
    }

    public LiveData<List<Photograph>> getPhotographsInCity() {
        return photographsInCity;
    }

    public LiveData<List<PhotographService>> getServices() {
        return services;
    }
}
