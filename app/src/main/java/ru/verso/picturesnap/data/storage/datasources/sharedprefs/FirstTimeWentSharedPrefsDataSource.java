package ru.verso.picturesnap.data.storage.datasources.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

import ru.verso.picturesnap.data.storage.datasources.FirstTimeWentDataSource;

public class FirstTimeWentSharedPrefsDataSource implements FirstTimeWentDataSource {

    public static final String FIRST_WENT_KEY = "FIRST_WENT";

    public static final String SHARED_PREFERENCES_PATH = "PICTURESNAP_PREFERENCES";

    private final SharedPreferences.Editor editor;
    private final SharedPreferences sharedPreferences;

    private static final boolean DEFAULT_VALUE = true;

    public FirstTimeWentSharedPrefsDataSource(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_PATH, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public boolean isFirst() {
        return sharedPreferences.getBoolean(FIRST_WENT_KEY, DEFAULT_VALUE);
    }

    @Override
    public void setVisited() {
        editor.putBoolean(FIRST_WENT_KEY, false);
        editor.apply();
    }
}
