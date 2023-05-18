package ru.verso.picturesnap.data.repository;

import com.google.firebase.auth.FirebaseAuth;

import ru.verso.picturesnap.domain.repository.UserAuthDataRepository;

public class UserAuthDataRepositoryImpl implements UserAuthDataRepository {

    private final FirebaseAuth firebaseAuth;

    public UserAuthDataRepositoryImpl() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public String getId() {
        return firebaseAuth.getUid();
    }
}