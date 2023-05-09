package ru.verso.picturesnap.domain.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photographer;

public interface FavoritesRepository {

    LiveData<List<Photographer>> getAllFavorites();

    void addFavorite(Photographer photographer);

    void deleteFavorite(Photographer photographer);
}
