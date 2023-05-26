package ru.verso.picturesnap.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.data.storage.datasources.ServicesDataSource;
import ru.verso.picturesnap.domain.models.PhotographerService;
import ru.verso.picturesnap.domain.repository.ServicesRepository;

public class ServicesRepositoryImpl implements ServicesRepository {

    private final ServicesDataSource servicesDataSource;

    public ServicesRepositoryImpl(ServicesDataSource servicesDataSource) {
        this.servicesDataSource = servicesDataSource;
    }

    @Override
    public LiveData<List<PhotographerService>> getAllServices() {
        return servicesDataSource.getAllServices();
    }
}
