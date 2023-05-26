package ru.verso.picturesnap.data.repository;

import ru.verso.picturesnap.data.storage.datasources.UserLocationDataSource;
import ru.verso.picturesnap.domain.models.Location;
import ru.verso.picturesnap.domain.repository.UserLocationRepository;

public class UserLocationRepositoryImpl implements UserLocationRepository {

    private final UserLocationDataSource userLocationDataSource;

    public UserLocationRepositoryImpl(UserLocationDataSource userLocationDataSource) {
        this.userLocationDataSource = userLocationDataSource;
    }

    @Override
    public Location getLocation() {
        return userLocationDataSource.getLocation();
    }

    @Override
    public void setLocation(double latitude, double longitude) {
        userLocationDataSource.setLocation(latitude, longitude);
    }
}
