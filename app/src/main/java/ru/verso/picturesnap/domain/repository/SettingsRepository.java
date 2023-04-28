package ru.verso.picturesnap.domain.repository;

public interface SettingsRepository {

    void enableNotifications();
    void disableNotifications();
    boolean getNotificationsState();
}
