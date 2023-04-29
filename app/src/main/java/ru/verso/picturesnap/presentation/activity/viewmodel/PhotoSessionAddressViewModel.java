package ru.verso.picturesnap.presentation.activity.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.verso.picturesnap.domain.models.Location;

public class PhotoSessionAddressViewModel extends ViewModel {

    private final MutableLiveData<Location> photoSessionLocation = new MutableLiveData<>();

    public void putPhotoSessionAddress(Location location) {
        photoSessionLocation.setValue(location);
    }

    public LiveData<Location> getPhotoSessionLocation() {
        return photoSessionLocation;
    }
}
