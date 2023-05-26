package ru.verso.picturesnap.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.data.storage.PhotographerPresentationServiceDataSource;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.domain.repository.PhotographerPresentationServiceRepository;

public class PhotographerPresentationServiceRepositoryImpl implements PhotographerPresentationServiceRepository {

    private final PhotographerPresentationServiceDataSource photographerPresentationServiceDataSource;

    public PhotographerPresentationServiceRepositoryImpl(PhotographerPresentationServiceDataSource photographerPresentationServiceDataSource) {
        this.photographerPresentationServiceDataSource = photographerPresentationServiceDataSource;
    }

    @Override
    public LiveData<List<PhotographerPresentationService>> getAllPresentationServices() {
        return photographerPresentationServiceDataSource.getAllPresentationServices();
    }

    @Override
    public LiveData<List<PhotographerPresentationService>> getPresentationServicesByServiceId(String id) {
        return photographerPresentationServiceDataSource.getPresentationServicesByServiceId(id);
    }
}