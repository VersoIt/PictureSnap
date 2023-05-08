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
import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.models.PhotographService;
import ru.verso.picturesnap.domain.usecase.GetPhotographDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;
import ru.verso.picturesnap.presentation.app.PictureSnapApp;
import ru.verso.picturesnap.presentation.utils.LocationCoordinator;

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
        if (userLocation.getLatitude() == 0 && userLocation.getLongitude() == 0)
            return new ArrayList<>();

        String userCity = LocationCoordinator.getCityNameByLocation(context, userLocation.getLatitude(), userLocation.getLongitude());

        return Objects.requireNonNull(photographs)
                .stream().filter(p ->
                        LocationCoordinator.getCityNameByLocation(context, p.getLatitude(),
                                p.getLongitude()).contains(
                                userCity))
                .limit(PictureSnapApp.PHOTOGRAPH_IN_CITY_LIST_LIMIT)
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
