package ru.verso.picturesnap.presentation.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.GetApplicationSettingsDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateApplicationSettingsUseCase;
import ru.verso.picturesnap.presentation.activity.viewmodel.SettingsViewModel;

public class SettingsViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final GetApplicationSettingsDataUseCase getApplicationSettingsDataUseCase;

    @NonNull
    private final GetUserDataUseCase getUserDataUseCase;

    @NonNull
    private final UpdateApplicationSettingsUseCase updateApplicationSettingsUseCase;

    public SettingsViewModelFactory(@NonNull GetApplicationSettingsDataUseCase getApplicationSettingsDataUseCase,
                                    @NonNull GetUserDataUseCase getUserDataUseCase,
                                    @NonNull UpdateApplicationSettingsUseCase updateApplicationSettingsUseCase) {
        this.getApplicationSettingsDataUseCase = getApplicationSettingsDataUseCase;
        this.getUserDataUseCase = getUserDataUseCase;
        this.updateApplicationSettingsUseCase = updateApplicationSettingsUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SettingsViewModel(getApplicationSettingsDataUseCase, getUserDataUseCase, updateApplicationSettingsUseCase);
    }
}
