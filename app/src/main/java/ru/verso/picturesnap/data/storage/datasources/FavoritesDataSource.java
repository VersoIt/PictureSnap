package ru.verso.picturesnap.data.storage.datasources;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photographer;

public interface FavoritesDataSource {

    LiveData<List<Photographer>> getAllFavorites();

    void addFavorite(Photographer photographer);

    void deleteFavorite(Photographer photographer);
}
