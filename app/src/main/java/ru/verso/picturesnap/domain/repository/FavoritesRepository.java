package ru.verso.picturesnap.domain.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photograph;

public interface FavoritesRepository {

    LiveData<List<Photograph>> getAllFavorites();

    void addFavorite(Photograph photograph);

    void deleteFavorite(Photograph photograph);
}
