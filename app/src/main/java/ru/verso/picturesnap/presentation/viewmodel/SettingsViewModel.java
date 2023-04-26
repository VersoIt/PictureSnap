package ru.verso.picturesnap.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.domain.repository.SettingsRepository;

public class SettingsViewModel extends ViewModel {

    private final SettingsRepository settingsRepository;

    private final RoleRepository roleRepository;

    private final MutableLiveData<Boolean> notificationsState;

    public SettingsViewModel(SettingsRepository settingsRepository, RoleRepository roleRepository) {
        this.settingsRepository = settingsRepository;
        notificationsState = new MutableLiveData<>(settingsRepository.getState());
        this.roleRepository = roleRepository;
    }

    public void enableNotifications() {
        settingsRepository.enableNotifications();
        notificationsState.setValue(true);
    }

    public void disableNotifications() {
        settingsRepository.disableNotifications();
        notificationsState.setValue(false);
    }

    public LiveData<Boolean> getNotificationsState() {
        return notificationsState;
    }

    public RoleRepository.Role getUserRole() {
        return roleRepository.getRole();
    }
}