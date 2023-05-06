package ru.verso.picturesnap.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import ru.verso.picturesnap.data.storage.room.entity.PhotographEntity;
import ru.verso.picturesnap.data.storage.room.root.AppDatabase;
import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.repository.FavoritesRepository;

public class FavoritesRepositoryImpl implements FavoritesRepository {

    private final AppDatabase database;

    public FavoritesRepositoryImpl(Context context) {
        database = AppDatabase.getDatabase(context);
    }

    @Override
    public LiveData<List<Photograph>> getAllFavorites() {
        LiveData<List<PhotographEntity>> favoritesEntity = database.favoritesDAO().getAllFavorites();
        return Transformations.map(
                favoritesEntity,
                values -> values.stream().map(PhotographEntity::mapToDomain).collect(Collectors.toList())
        );
    }

    @Override
    public void addFavorite(Photograph photograph) {
        AppDatabase.databaseWriteExecutor.execute(() -> database.favoritesDAO().addFavorite(mapPhotographToData(photograph)));
    }

    @Override
    public void deleteFavorite(Photograph photograph) {
        AppDatabase.databaseWriteExecutor.execute(() -> database.favoritesDAO().removeFavorite(mapPhotographToData(photograph)));
    }

    private PhotographEntity mapPhotographToData(Photograph photograph) {

        return new PhotographEntity.Builder()
                .setName(photograph.getFirstName(), photograph.getLastName())
                .setRating(photograph.getRating())
                .setExperience(photograph.getExperience())
                .setPhoneNumber(photograph.getPhoneNumber())
                .setDescription(photograph.getDescription())
                .setEmail(photograph.getEmail())
                .setId(photograph.getId())
                .setLocation(photograph.getLatitude(), photograph.getLongitude())
                .create();
    }
}
