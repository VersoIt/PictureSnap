package ru.verso.picturesnap.domain.models;

import java.util.Objects;

public class Location {

    private final double latitude;
    private final double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (other instanceof Location) {
            Location otherLocation = (Location) other;
            return latitude == otherLocation.latitude &&
                    longitude == otherLocation.getLongitude();
        }

        return false;
    }

    public boolean isInvalid() {
        return latitude == 0 && longitude == 0;
    }
}