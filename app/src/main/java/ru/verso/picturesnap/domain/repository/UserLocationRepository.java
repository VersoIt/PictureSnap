package ru.verso.picturesnap.domain.repository;

public interface UserLocationRepository {

    String DEFAULT_VALUE = "Москва";

    String getLocation();

    void setLocation(String location);
}
