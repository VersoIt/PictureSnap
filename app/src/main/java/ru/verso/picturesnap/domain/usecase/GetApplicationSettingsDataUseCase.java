package ru.verso.picturesnap.domain.usecase;

import ru.verso.picturesnap.domain.repository.SettingsRepository;

public class GetApplicationSettingsDataUseCase {

    private final SettingsRepository settingsRepository;

    public GetApplicationSettingsDataUseCase(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public boolean getNotificationsState() {
        return settingsRepository.getNotificationsState();
    }
}
