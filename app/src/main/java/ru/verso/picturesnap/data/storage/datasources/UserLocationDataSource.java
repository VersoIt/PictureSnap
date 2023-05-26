package ru.verso.picturesnap.data.storage.datasources;

import ru.verso.picturesnap.domain.models.Location;

public interface UserLocationDataSource {

    Location getLocation();

    void setLocation(double latitude, double longitude);
}
