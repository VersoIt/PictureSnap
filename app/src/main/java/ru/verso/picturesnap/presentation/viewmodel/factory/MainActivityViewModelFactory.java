package ru.verso.picturesnap.presentation.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.presentation.viewmodel.MainActivityViewModel;

public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final RoleRepository repository;

    public MainActivityViewModelFactory(@NonNull RoleRepository repository) {
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(repository);
    }
}
