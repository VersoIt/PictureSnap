package ru.verso.picturesnap.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.verso.picturesnap.domain.models.PhotographService;
import ru.verso.picturesnap.domain.repository.ClientRepository;
import ru.verso.picturesnap.domain.repository.UserLocationRepository;

public class UnregisteredMainViewModel extends ViewModel {

    private final LiveData<List<PhotographService>> services;

    private final MutableLiveData<String> location;

    private final UserLocationRepository locationRepository;

    public UnregisteredMainViewModel(ClientRepository clientRepository, UserLocationRepository locationRepository) {
        services = clientRepository.getPhotographServices();
        this.locationRepository = locationRepository;

        location = new MutableLiveData<>(locationRepository.getLocation());
    }

    public void setLocation(String location) {
        locationRepository.setLocation(location);
    }

    public LiveData<String> getLocation() {
        this.location.setValue(locationRepository.getLocation());
        return location;
    }

    public LiveData<List<PhotographService>> getServices() {
        return services;
    }
}
