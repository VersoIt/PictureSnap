package ru.verso.picturesnap.data.repository;

import android.net.Uri;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.data.storage.datasources.PhotographerDataSource;
import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.domain.models.PhotographerService;
import ru.verso.picturesnap.domain.repository.PhotographerRepository;

public class PhotographerRepositoryImpl implements PhotographerRepository {
    private final PhotographerDataSource photographerDataSource;

    public PhotographerRepositoryImpl(PhotographerDataSource photographerDataSource) {
        this.photographerDataSource = photographerDataSource;
    }

    @Override
    public LiveData<List<Photographer>> getAllPhotographers() {
        return photographerDataSource.getAllPhotographers();
    }

    @Override
    public LiveData<List<PhotographerService>> getAllPhotographerServices() {
        return photographerDataSource.getAllPhotographerServices();
    }

    @Override
    public LiveData<Photographer> getPhotographerById(String id) {
        return photographerDataSource.getPhotographerById(id);
    }

    @Override
    public LiveData<List<PhotographerPresentationService>> getPhotographerServicesById(String photographerId) {
        return photographerDataSource.getPhotographerServicesById(photographerId);
    }

    @Override
    public void updatePhotographerAvatar(String photographerId, Uri path) {
        photographerDataSource.updatePhotographerAvatar(photographerId, path);
    }

    @Override
    public void updateRatingOf(String photographerId, float lastAverage, int lastCount, int newAdd) {
        photographerDataSource.updateRatingOf(photographerId, lastAverage, lastCount, newAdd);
    }

    @Override
    public LiveData<PhotographerPresentationService> getServiceById(String id) {
        return photographerDataSource.getServiceById(id);
    }
}