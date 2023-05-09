package ru.verso.picturesnap.presentation.viewmodel.unregistered;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ru.verso.picturesnap.domain.models.Location;
import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.models.PhotographerService;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;
import ru.verso.picturesnap.presentation.app.PictureSnapApp;
import ru.verso.picturesnap.presentation.utils.LocationCoordinator;

public class UnregisteredMainViewModel extends ViewModel {

    private final LiveData<List<PhotographerService>> services;

    private final MutableLiveData<Location> location;

    private final GetUserDataUseCase getUserDataUseCase;

    private final GetPhotographerDataUseCase getPhotographerDataUseCase;

    private final MutableLiveData<List<Photographer>> photographersInCity;

    private final Application context;

    public UnregisteredMainViewModel(Application context,
                                     GetPhotographerDataUseCase getPhotographerDataUseCase,
                                     GetUserDataUseCase getUserDataUseCase,
                                     UpdateUserDataUseCase updateUserDataUseCase) {

        this.context = context;
        this.getUserDataUseCase = getUserDataUseCase;
        this.getPhotographerDataUseCase = getPhotographerDataUseCase;

        services = getPhotographerDataUseCase.getPhotographerServices();
        location = new MutableLiveData<>(getUserDataUseCase.getLocation());
        photographersInCity = new MutableLiveData<>();
    }

    public LiveData<List<Photographer>> getAllPhotographers() {
        return getPhotographerDataUseCase.getAllPhotographers();
    }

    private List<Photographer> getPhotographersByLocation(List<Photographer> photographers, Location userLocation) {
        if (userLocation.getLatitude() == 0 && userLocation.getLongitude() == 0)
            return new ArrayList<>();

        String userCity = LocationCoordinator.getCityNameByLocation(context, userLocation.getLatitude(), userLocation.getLongitude());

        return Objects.requireNonNull(photographers)
                .stream().filter(p ->
                        LocationCoordinator.getCityNameByLocation(context, p.getLatitude(),
                                p.getLongitude()).contains(
                                userCity))
                .limit(PictureSnapApp.PHOTOGRAPH_IN_CITY_LIST_LIMIT)
                .collect(Collectors.toList());
    }

    public void updatePhotographersInCity(List<Photographer> photographers) {
        photographersInCity.setValue(getPhotographersByLocation(photographers, getUserDataUseCase.getLocation()));
    }

    public LiveData<Location> getLocation() {
        this.location.setValue(getUserDataUseCase.getLocation());
        return location;
    }

    public LiveData<List<Photographer>> getPhotographersInCity() {
        return photographersInCity;
    }

    public LiveData<List<PhotographerService>> getServices() {
        return services;
    }
}
