package ru.verso.picturesnap.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.data.storage.datasources.FavoritesDataSource;
import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.repository.FavoritesRepository;

public class FavoritesRepositoryImpl implements FavoritesRepository {

    private final FavoritesDataSource favoritesDataSource;

    public FavoritesRepositoryImpl(FavoritesDataSource favoritesDataSource) {
        this.favoritesDataSource = favoritesDataSource;
    }

    @Override
    public LiveData<List<Photographer>> getAllFavorites() {
        return favoritesDataSource.getAllFavorites();
    }

    @Override
    public void addFavorite(Photographer photographer) {
        favoritesDataSource.addFavorite(photographer);
    }

    @Override
    public void deleteFavorite(Photographer photographer) {
        favoritesDataSource.deleteFavorite(photographer);
    }
}
