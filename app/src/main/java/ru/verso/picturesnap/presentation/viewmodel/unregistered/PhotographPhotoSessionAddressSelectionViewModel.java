package ru.verso.picturesnap.presentation.viewmodel.unregistered;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.verso.picturesnap.domain.models.Location;

public class PhotographPhotoSessionAddressSelectionViewModel extends ViewModel {

    public static final Location defaultLocation = new Location(55.663730, 37.469243);

    private final MutableLiveData<String> currentLocationString = new MutableLiveData<>("");

    private final MutableLiveData<Location> currentLocation = new MutableLiveData<>(defaultLocation);

    public LiveData<String> getCurrentLocationString() {
        return currentLocationString;
    }

    public void setLocation(Location location) {
        currentLocation.setValue(location);
    }

    public LiveData<Location> getLocation() {
        return currentLocation;
    }

    public LiveData<String> getLocationString() {
        return currentLocationString;
    }

    public void setLocationString(String location) {
        currentLocationString.setValue(location);
    }
}
