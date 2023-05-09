package ru.verso.picturesnap.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import ru.verso.picturesnap.data.storage.room.entity.PhotographerEntity;
import ru.verso.picturesnap.data.storage.room.root.AppDatabase;
import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.repository.FavoritesRepository;

public class FavoritesRepositoryImpl implements FavoritesRepository {

    private final AppDatabase database;

    public FavoritesRepositoryImpl(Context context) {
        database = AppDatabase.getDatabase(context);
    }

    @Override
    public LiveData<List<Photographer>> getAllFavorites() {
        LiveData<List<PhotographerEntity>> favoritesEntity = database.favoritesDAO().getAllFavorites();
        return Transformations.map(
                favoritesEntity,
                values -> values.stream().map(PhotographerEntity::mapToDomain).collect(Collectors.toList())
        );
    }

    @Override
    public void addFavorite(Photographer photographer) {
        AppDatabase.databaseWriteExecutor.execute(() -> database.favoritesDAO().addFavorite(mapPhotographerToData(photographer)));
    }

    @Override
    public void deleteFavorite(Photographer photographer) {
        AppDatabase.databaseWriteExecutor.execute(() -> database.favoritesDAO().removeFavorite(mapPhotographerToData(photographer)));
    }

    private PhotographerEntity mapPhotographerToData(Photographer photographer) {

        return new PhotographerEntity.Builder()
                .setName(photographer.getFirstName(), photographer.getLastName())
                .setRating(photographer.getRating())
                .setExperience(photographer.getExperience())
                .setPhoneNumber(photographer.getPhoneNumber())
                .setDescription(photographer.getDescription())
                .setEmail(photographer.getEmail())
                .setId(photographer.getId())
                .setLocation(photographer.getLatitude(), photographer.getLongitude())
                .create();
    }
}
