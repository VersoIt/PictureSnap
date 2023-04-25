package ru.verso.picturesnap.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import ru.verso.picturesnap.domain.repository.VerificationCodeRepository;

public class VerificationCodeRepositoryImpl implements VerificationCodeRepository {

    public static final String VERIFICATION_KEY = "VERIFICATION";

    public static final String SHARED_PREFERENCES_PATH = "PICTURESNAP_PREFERENCES";

    private final SharedPreferences.Editor editor;
    private final SharedPreferences sharedPreferences;

    public VerificationCodeRepositoryImpl(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_PATH, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public String getVerificationKey() {
        return sharedPreferences.getString(VERIFICATION_KEY, null);
    }

    @Override
    public void updateVerificationKey(String key) {
        editor.putString(VERIFICATION_KEY, key);
    }
}
