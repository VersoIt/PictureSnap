package ru.verso.picturesnap.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.usecase.GetFavoritesDataUseCase;

public class FavoritesViewModel extends ViewModel {

    private final GetFavoritesDataUseCase getFavoritesDataUseCase;

    public FavoritesViewModel(GetFavoritesDataUseCase getFavoritesDataUseCase) {
        this.getFavoritesDataUseCase = getFavoritesDataUseCase;
    }

    public LiveData<List<Photograph>> getAllFavorites() {
        return getFavoritesDataUseCase.getAllFavorites();
    }

    public void addFavorite(Photograph photograph) {
        getFavoritesDataUseCase.addFavorite(photograph);
    }

    public void deleteFavorite(Photograph photograph) {
        getFavoritesDataUseCase.deleteFavorite(photograph);
    }
}
