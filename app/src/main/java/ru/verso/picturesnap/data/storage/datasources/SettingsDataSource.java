package ru.verso.picturesnap.data.storage.datasources;

public interface SettingsDataSource {

    void enableNotifications();

    void disableNotifications();

    boolean getNotificationsState();
}
