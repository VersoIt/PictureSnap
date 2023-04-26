package ru.verso.picturesnap.presentation.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.domain.repository.SettingsRepository;
import ru.verso.picturesnap.presentation.viewmodel.SettingsViewModel;

public class SettingsViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final SettingsRepository settingsRepository;

    @NonNull
    private final RoleRepository roleRepository;

    public SettingsViewModelFactory(@NonNull SettingsRepository repository, @NonNull RoleRepository roleRepository) {
        this.settingsRepository = repository;
        this.roleRepository = roleRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SettingsViewModel(settingsRepository, roleRepository);
    }
}
