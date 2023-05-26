package ru.verso.picturesnap.data.storage.datasources.firebase;

import com.google.firebase.auth.FirebaseAuth;

import ru.verso.picturesnap.data.storage.datasources.UserAuthDataSource;

public class UserAuthFirebaseDataSource implements UserAuthDataSource {

    private final FirebaseAuth firebaseAuth;

    public UserAuthFirebaseDataSource() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public String getId() {
        if (firebaseAuth.getCurrentUser() == null)
            return "";

        String uId = firebaseAuth.getUid();
        if (uId == null)
            return "";

        return uId;
    }
}
