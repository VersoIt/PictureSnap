package ru.verso.picturesnap.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;

import ru.verso.picturesnap.domain.repository.RoleRepository;

public class RoleRepositoryImpl implements RoleRepository {

    public static final String ROLE_KEY = "ROLE";

    public static final String SHARED_PREFERENCES_PATH = "PICTURESNAP_PREFERENCES";

    private final SharedPreferences.Editor editor;
    private final SharedPreferences sharedPreferences;

    private final FirebaseAuth firebaseAuth;

    public RoleRepositoryImpl(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_PATH, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public Role getRole() {
        if (firebaseAuth.getCurrentUser() == null)
            return Role.UNREGISTERED;

        return Role.values()[sharedPreferences.getInt(ROLE_KEY, DEFAULT)];
    }

    @Override
    public void updateRole(Role role) {
        editor.putInt(ROLE_KEY, role.ordinal());
        editor.apply();
    }
}
