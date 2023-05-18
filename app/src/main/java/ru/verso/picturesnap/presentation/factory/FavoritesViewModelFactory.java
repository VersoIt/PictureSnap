package ru.verso.picturesnap.presentation.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.GetFavoritesDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.FavoritesViewModel;

public class FavoritesViewModelFactory implements ViewModelProvider.Factory {

    private final GetFavoritesDataUseCase getFavoritesDataUseCase;

    private final GetUserDataUseCase getUserDataUseCase;

    public FavoritesViewModelFactory(GetFavoritesDataUseCase getFavoritesDataUseCase, GetUserDataUseCase getUserDataUseCase) {
        this.getFavoritesDataUseCase = getFavoritesDataUseCase;
        this.getUserDataUseCase = getUserDataUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new FavoritesViewModel(getFavoritesDataUseCase, getUserDataUseCase);
    }
}
