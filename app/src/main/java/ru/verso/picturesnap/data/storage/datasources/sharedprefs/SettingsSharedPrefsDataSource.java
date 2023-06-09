package ru.verso.picturesnap.data.storage.datasources.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

import ru.verso.picturesnap.data.storage.datasources.SettingsDataSource;

public class SettingsSharedPrefsDataSource implements SettingsDataSource {

    private final SharedPreferences.Editor editor;
    private final SharedPreferences sharedPreferences;

    public static final String SHARED_PREFERENCES_PATH = "PICTURESNAP_SETTINGS";

    public static final String NOTIFICATIONS_KEY = "NOTIFICATIONS_KEY";

    private static final boolean DEFAULT = true;

    public SettingsSharedPrefsDataSource(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_PATH, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public void enableNotifications() {
        editor.putBoolean(NOTIFICATIONS_KEY, true);
        editor.apply();
    }

    @Override
    public void disableNotifications() {
        editor.putBoolean(NOTIFICATIONS_KEY, false);
        editor.apply();
    }

    @Override
    public boolean getNotificationsState() {
        return sharedPreferences.getBoolean(NOTIFICATIONS_KEY, DEFAULT);
    }
}
