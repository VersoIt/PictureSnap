package ru.verso.picturesnap.domain.usecase;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.repository.FavoritesRepository;

public class GetFavoritesDataUseCase {

    private final FavoritesRepository favoritesRepository;

    public GetFavoritesDataUseCase(FavoritesRepository favoritesRepository) {
        this.favoritesRepository = favoritesRepository;
    }

    public LiveData<List<Photographer>> getAllFavorites() {
        return favoritesRepository.getAllFavorites();
    }

    public void addFavorite(Photographer photographer) {
        favoritesRepository.addFavorite(photographer);
    }

    public void deleteFavorite(Photographer photographer) {
        favoritesRepository.deleteFavorite(photographer);
    }
}
