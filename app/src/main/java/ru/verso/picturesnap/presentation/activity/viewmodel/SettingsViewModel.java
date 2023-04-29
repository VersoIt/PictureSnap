package ru.verso.picturesnap.presentation.activity.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.domain.repository.SettingsRepository;
import ru.verso.picturesnap.domain.usecase.GetApplicationSettingsDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateApplicationSettingsUseCase;

public class SettingsViewModel extends ViewModel {

    private final GetApplicationSettingsDataUseCase getApplicationSettingsDataUseCase;

    private final GetUserDataUseCase getUserDataUseCase;

    private final MutableLiveData<Boolean> notificationsState;

    private final UpdateApplicationSettingsUseCase updateApplicationSettingsUseCase;

    public SettingsViewModel(GetApplicationSettingsDataUseCase getApplicationSettingsDataUseCase,
                             GetUserDataUseCase getUserDataUseCase,
                             UpdateApplicationSettingsUseCase updateApplicationSettingsUseCase) {
        this.getApplicationSettingsDataUseCase = getApplicationSettingsDataUseCase;
        notificationsState = new MutableLiveData<>(getApplicationSettingsDataUseCase.getNotificationsState());
        this.getUserDataUseCase = getUserDataUseCase;
        this.updateApplicationSettingsUseCase = updateApplicationSettingsUseCase;
    }

    public void enableNotifications() {
        updateApplicationSettingsUseCase.enableNotifications();
        notificationsState.setValue(true);
    }

    public void disableNotifications() {
        updateApplicationSettingsUseCase.disableNotifications();
        notificationsState.setValue(false);
    }

    public LiveData<Boolean> getNotificationsState() {
        return notificationsState;
    }

    public RoleRepository.Role getUserRole() {
        return getUserDataUseCase.getRole();
    }
}