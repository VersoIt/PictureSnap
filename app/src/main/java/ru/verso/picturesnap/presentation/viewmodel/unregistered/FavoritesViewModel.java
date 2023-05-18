package ru.verso.picturesnap.presentation.viewmodel.unregistered;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.domain.usecase.GetFavoritesDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;

public class FavoritesViewModel extends ViewModel {

    private final GetFavoritesDataUseCase getFavoritesDataUseCase;
    private final GetUserDataUseCase getUserDataUseCase;

    public FavoritesViewModel(GetFavoritesDataUseCase getFavoritesDataUseCase, GetUserDataUseCase getUserDataUseCase) {
        this.getFavoritesDataUseCase = getFavoritesDataUseCase;
        this.getUserDataUseCase = getUserDataUseCase;
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

    public boolean isAuthorized() {
        return getUserDataUseCase.getRole() == RoleRepository.Role.CLIENT;
    }
}
