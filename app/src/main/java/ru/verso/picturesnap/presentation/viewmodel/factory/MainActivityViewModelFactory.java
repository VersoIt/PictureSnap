package ru.verso.picturesnap.presentation.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.presentation.viewmodel.MainActivityViewModel;

public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final GetUserDataUseCase getUserDataUseCase;

    public MainActivityViewModelFactory(@NonNull GetUserDataUseCase getUserDataUseCase) {
        this.getUserDataUseCase = getUserDataUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(getUserDataUseCase);
    }
}
