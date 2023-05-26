package ru.verso.picturesnap.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.data.storage.datasources.PhotographerServiceDataSource;
import ru.verso.picturesnap.domain.models.PhotographerService;
import ru.verso.picturesnap.domain.repository.PhotographerServiceRepository;

public class PhotographerServiceRepositoryImpl implements PhotographerServiceRepository {

    private final PhotographerServiceDataSource photographerServiceDataSource;

    public PhotographerServiceRepositoryImpl(PhotographerServiceDataSource photographerServiceDataSource) {
        this.photographerServiceDataSource = photographerServiceDataSource;
    }

    @Override
    public LiveData<List<PhotographerService>> getAllServices() {
        return photographerServiceDataSource.getAllServices();
    }

    @Override
    public LiveData<PhotographerService> getServiceById(String id) {
        return photographerServiceDataSource.getServiceById(id);
    }
}
