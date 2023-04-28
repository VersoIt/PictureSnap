package ru.verso.picturesnap.domain.usecase;

import ru.verso.picturesnap.domain.repository.SettingsRepository;

public class UpdateApplicationSettingsUseCase {

    private final SettingsRepository settingsRepository;

    public UpdateApplicationSettingsUseCase(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public void enableNotifications() {
        settingsRepository.enableNotifications();
    }

    public void disableNotifications() {
        settingsRepository.disableNotifications();
    }
}
