package ru.verso.picturesnap.data.storage.datasources.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

import ru.verso.picturesnap.data.storage.datasources.UserLocationDataSource;
import ru.verso.picturesnap.domain.models.Location;

public class UserLocationSharedPrefsDataSource implements UserLocationDataSource {

    public static final String SHARED_PREFERENCES_PATH = "PICTURESNAP_PREFERENCES";

    public static final String LATITUDE_KEY = "LATITUDE";
    public static final String LONGITUDE_KEY = "LONGITUDE";

    private static final double DEF_VALUE = 0.0;

    private final SharedPreferences.Editor editor;
    private final SharedPreferences sharedPreferences;

    public UserLocationSharedPrefsDataSource(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_PATH, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public Location getLocation() {
        double latitude = sharedPreferences.getFloat(LATITUDE_KEY, (float) DEF_VALUE);
        double longitude = sharedPreferences.getFloat(LONGITUDE_KEY, (float) DEF_VALUE);

        return new Location(latitude, longitude);
    }

    @Override
    public void setLocation(double latitude, double longitude) {
        editor.putFloat(LATITUDE_KEY, (float) latitude);
        editor.apply();

        editor.putFloat(LONGITUDE_KEY, (float) longitude);
        editor.apply();
    }
}
