package ru.verso.picturesnap.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.data.storage.room.root.AppDatabase;
import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.repository.FavoritesRepository;

public class FavoritesRepositoryImpl implements FavoritesRepository {

    private final AppDatabase database;

    public FavoritesRepositoryImpl(Context context) {
        database = AppDatabase.getDatabase(context);
    }

    @Override
    public LiveData<List<Photograph>> getFavoritesOf(int clientId) {
//        LiveData<List<PhotographEntity>> favoritesEntity = database.favoritesDAO().getFavoritesByClientId(clientId);
//        return Transformations.map(
//                favoritesEntity,
//                values -> values.stream().map(PhotographEntity::mapToDomain).collect(Collectors.toList())
//        );
        return null;
    }

    @Override
    public LiveData<Photograph> findPhotographById() {
        return null;
    }
}
