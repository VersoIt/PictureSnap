package ru.verso.picturesnap.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import ru.verso.picturesnap.data.storage.room.entity.PhotographEntity;
import ru.verso.picturesnap.data.storage.room.entity.PhotographServiceEntity;
import ru.verso.picturesnap.data.storage.room.root.AppDatabase;
import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.models.PhotographService;
import ru.verso.picturesnap.domain.repository.PhotographRepository;

public class PhotographRepositoryImpl implements PhotographRepository {

    private final AppDatabase appDatabase;

    public PhotographRepositoryImpl(Context application) {
        appDatabase = AppDatabase.getDatabase(application);
    }

    @Override
    public LiveData<List<Photograph>> getAllPhotographs() {
        return mapPhotographsEntityToDomain(appDatabase.photographDAO().getAllPhotographs());
    }

    @Override
    public LiveData<List<PhotographService>> getAllPhotographServices() {
        return mapPhotographServicesToDomain(appDatabase.photographServiceDAO().getAllServices());
    }

    @Override
    public LiveData<Photograph> getPhotographById(int id) {
        LiveData<PhotographEntity> photographEntityLiveData = appDatabase.photographDAO().getPhotographById(id);
        return Transformations.map(
                photographEntityLiveData,
                PhotographEntity::mapToDomain
        );
    }

    @Override
    public LiveData<List<PhotographService>> getPhotographServicesById(int photographId) {
        LiveData<List<PhotographServiceEntity>> entity = appDatabase.photographServiceDAO().getServicesOfPhotograph(photographId);
        return Transformations.map(
                entity,
                values -> values.stream().map(PhotographServiceEntity::mapToDomain).collect(Collectors.toList())
        );
    }

    private LiveData<List<PhotographService>> mapPhotographServicesToDomain(LiveData<List<PhotographServiceEntity>> entity) {
        return Transformations.map(
                entity,
                values -> values.stream().map(PhotographServiceEntity::mapToDomain).collect(Collectors.toList())
        );
    }

    private LiveData<List<Photograph>> mapPhotographsEntityToDomain(LiveData<List<PhotographEntity>> entity) {
        return Transformations.map(
                entity,
                values -> values.stream().map(PhotographEntity::mapToDomain).collect(Collectors.toList())
        );
    }
}