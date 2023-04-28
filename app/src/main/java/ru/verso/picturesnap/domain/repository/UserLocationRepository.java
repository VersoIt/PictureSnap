package ru.verso.picturesnap.domain.repository;

import ru.verso.picturesnap.domain.models.Location;

public interface UserLocationRepository {

    Location getLocation();

    void setLocation(double latitude, double longitude);
}
