package ru.verso.picturesnap.data.storage.datasources.room;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import ru.verso.picturesnap.data.storage.datasources.FavoritesDataSource;
import ru.verso.picturesnap.data.storage.room.entity.PhotographerEntity;
import ru.verso.picturesnap.data.storage.room.root.AppDatabase;
import ru.verso.picturesnap.domain.models.Photographer;

public class FavoritesRoomDataSource implements FavoritesDataSource {

    private final AppDatabase database;

    public FavoritesRoomDataSource(Context context) {
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
                .setAvatarPath(photographer.getAvatarPath())
                .setLocation(photographer.getLatitude(), photographer.getLongitude())
                .create();
    }
}
