package ru.verso.picturesnap.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import ru.verso.picturesnap.data.storage.room.entity.PhotographEntity;
import ru.verso.picturesnap.data.storage.room.root.AppDatabase;
import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.repository.PhotographRepository;

public class PhotographRepositoryImpl implements PhotographRepository {

    private final AppDatabase appDatabase;

    public PhotographRepositoryImpl(Application application) {
        appDatabase = AppDatabase.getDatabase(application);
    }

    @Override
    public LiveData<List<Photograph>> getPhotographsByLocation(String location) {
        return mapPhotographsEntityToDomain(appDatabase.photographDAO().getPhotographsByLocation(location));
    }

    private LiveData<List<Photograph>> mapPhotographsEntityToDomain(LiveData<List<PhotographEntity>> entity) {
        return Transformations.map(
                entity,
                values -> values.stream().map(PhotographEntity::mapToDomain).collect(Collectors.toList())
        );
    }
}