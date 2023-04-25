package ru.verso.picturesnap.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import ru.verso.picturesnap.domain.repository.UserLocationRepository;

public class UserLocationRepositoryImpl implements UserLocationRepository {

    public static final String SHARED_PREFERENCES_PATH = "PICTURESNAP_PREFERENCES";

    public static final String LOCATION_KEY = "LOCATION";

    private final SharedPreferences.Editor editor;
    private final SharedPreferences sharedPreferences;

    public UserLocationRepositoryImpl(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_PATH, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public String getLocation() {
        return sharedPreferences.getString(LOCATION_KEY, null);
    }

    @Override
    public void setLocation(String location) {
        editor.putString(LOCATION_KEY, location);
        editor.apply();
    }
}
