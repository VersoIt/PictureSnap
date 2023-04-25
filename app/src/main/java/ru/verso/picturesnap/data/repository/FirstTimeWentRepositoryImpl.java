package ru.verso.picturesnap.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import ru.verso.picturesnap.domain.repository.FirstTimeWentRepository;

public class FirstTimeWentRepositoryImpl implements FirstTimeWentRepository {

    public static final String FIRST_WENT_KEY = "FIRST_WENT";

    public static final String SHARED_PREFERENCES_PATH = "PICTURESNAP_PREFERENCES";

    private final SharedPreferences.Editor editor;
    private final SharedPreferences sharedPreferences;

    public FirstTimeWentRepositoryImpl(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_PATH, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public boolean isFirst() {
        return sharedPreferences.getInt(FIRST_WENT_KEY, 0) == 0;
    }

    @Override
    public void setVisited() {
        editor.putInt(FIRST_WENT_KEY, 1);
        editor.apply();
    }
}
