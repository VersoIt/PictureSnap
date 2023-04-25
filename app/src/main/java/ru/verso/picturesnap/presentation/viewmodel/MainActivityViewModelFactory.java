package ru.verso.picturesnap.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.repository.RoleRepository;

public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final RoleRepository repository;

    public MainActivityViewModelFactory(@NonNull RoleRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(repository);
    }
}
