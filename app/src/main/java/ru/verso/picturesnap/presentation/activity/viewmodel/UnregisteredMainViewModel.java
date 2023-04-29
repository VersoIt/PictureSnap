package ru.verso.picturesnap.presentation.activity.viewmodel;

import android.app.Application;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import ru.verso.picturesnap.domain.models.Location;
import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.models.PhotographService;
import ru.verso.picturesnap.domain.usecase.GetPhotographDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;
import ru.verso.picturesnap.utils.LocationCoordinator;

public class UnregisteredMainViewModel extends ViewModel {

    private final LiveData<List<PhotographService>> services;

    private final MutableLiveData<Location> location;

    private final GetUserDataUseCase getUserDataUseCase;

    private final GetPhotographDataUseCase getPhotographDataUseCase;

    private final MutableLiveData<List<Photograph>> photographsInCity;

    private final Application context;

    public UnregisteredMainViewModel(Application context,
                                     GetPhotographDataUseCase getPhotographDataUseCase,
                                     GetUserDataUseCase getUserDataUseCase,
                                     UpdateUserDataUseCase updateUserDataUseCase) {

        this.context = context;
        this.getUserDataUseCase = getUserDataUseCase;
        this.getPhotographDataUseCase = getPhotographDataUseCase;

        services = getPhotographDataUseCase.getPhotographServices();
        location = new MutableLiveData<>(getUserDataUseCase.getLocation());
        photographsInCity = new MutableLiveData<>();
    }

    public LiveData<List<Photograph>> getAllPhotographs() {
        return getPhotographDataUseCase.getAllPhotographs();
    }

    private List<Photograph> getPhotographsByLocation(List<Photograph> photographs, Location userLocation) {
        String userCity = LocationCoordinator.getCityNameByLocation(context, userLocation.getLatitude(), userLocation.getLongitude());

        return Objects.requireNonNull(photographs)
                .stream().filter(p ->
                        LocationCoordinator.getCityNameByLocation(context, p.getLatitude(),
                                p.getLongitude()).contains(
                                userCity))
                .collect(Collectors.toList());
    }

    public void updatePhotographsInCity(List<Photograph> photographs) {
        photographsInCity.setValue(getPhotographsByLocation(photographs, getUserDataUseCase.getLocation()));
    }

    public LiveData<Location> getLocation() {
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
