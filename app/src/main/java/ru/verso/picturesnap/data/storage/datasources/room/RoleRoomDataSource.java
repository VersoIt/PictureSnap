package ru.verso.picturesnap.data.storage.datasources.room;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;

import ru.verso.picturesnap.data.storage.datasources.RoleDataSource;
import ru.verso.picturesnap.domain.repository.RoleRepository;

public class RoleRoomDataSource implements RoleDataSource {
    public static final String ROLE_KEY = "ROLE";

    public static final String SHARED_PREFERENCES_PATH = "PICTURESNAP_PREFERENCES";

    private final SharedPreferences.Editor editor;
    private final SharedPreferences sharedPreferences;

    private final FirebaseAuth firebaseAuth;

    public RoleRoomDataSource(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_PATH, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public RoleRepository.Role getRole() {
        if (firebaseAuth.getCurrentUser() == null)
            return RoleRepository.Role.UNREGISTERED;

        return RoleRepository.Role.values()[sharedPreferences.getInt(ROLE_KEY, 0)];
    }

    @Override
    public void updateRole(RoleRepository.Role role) {
        editor.putInt(ROLE_KEY, role.ordinal());
        editor.apply();
    }
}
