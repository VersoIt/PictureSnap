package ru.verso.picturesnap.presentation.viewmodel.unregistered;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.usecase.GetFavoritesDataUseCase;

public class FavoritesViewModel extends ViewModel {

    private final GetFavoritesDataUseCase getFavoritesDataUseCase;

    public FavoritesViewModel(GetFavoritesDataUseCase getFavoritesDataUseCase) {
        this.getFavoritesDataUseCase = getFavoritesDataUseCase;
    }

    public LiveData<List<Photographer>> getAllFavorites() {
        return getFavoritesDataUseCase.getAllFavorites();
    }

    public void addFavorite(Photographer photographer) {
        getFavoritesDataUseCase.addFavorite(photographer);
    }

    public void deleteFavorite(Photographer photographer) {
        getFavoritesDataUseCase.deleteFavorite(photographer);
    }
}
