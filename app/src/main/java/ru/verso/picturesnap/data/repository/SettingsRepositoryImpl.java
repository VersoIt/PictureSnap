package ru.verso.picturesnap.data.repository;

import ru.verso.picturesnap.data.storage.datasources.SettingsDataSource;
import ru.verso.picturesnap.domain.repository.SettingsRepository;

public class SettingsRepositoryImpl implements SettingsRepository {

    private final SettingsDataSource settingsDataSource;

    public SettingsRepositoryImpl(SettingsDataSource settingsDataSource) {
        this.settingsDataSource = settingsDataSource;
    }

    @Override
    public void enableNotifications() {
        settingsDataSource.enableNotifications();
    }

    @Override
    public void disableNotifications() {
        settingsDataSource.disableNotifications();
    }

    @Override
    public boolean getNotificationsState() {
        return settingsDataSource.getNotificationsState();
    }
}
