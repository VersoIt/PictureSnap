package ru.verso.picturesnap.domain.usecase;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.repository.FavoritesRepository;

public class GetFavoritesDataUseCase {

    private final FavoritesRepository favoritesRepository;

    public GetFavoritesDataUseCase(FavoritesRepository favoritesRepository) {
        this.favoritesRepository = favoritesRepository;
    }

    public LiveData<List<Photograph>> getAllFavorites() {
        return favoritesRepository.getAllFavorites();
    }

    public void addFavorite(Photograph photograph) {
        favoritesRepository.addFavorite(photograph);
    }

    public void deleteFavorite(Photograph photograph) {
        favoritesRepository.deleteFavorite(photograph);
    }
}
