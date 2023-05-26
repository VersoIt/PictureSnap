package ru.verso.picturesnap.data.repository;

import androidx.lifecycle.LiveData;

import ru.verso.picturesnap.data.storage.datasources.SignInDataSource;
import ru.verso.picturesnap.domain.models.User;
import ru.verso.picturesnap.domain.repository.SignInCallback;
import ru.verso.picturesnap.domain.repository.SignInRepository;

public class SignInRepositoryImpl implements SignInRepository {

    private final SignInDataSource signInDataSource;

    public SignInRepositoryImpl(SignInDataSource signInDataSource) {
        this.signInDataSource = signInDataSource;
    }

    @Override
    public LiveData<User> signInUser(String email, String password, SignInCallback<User> callback) {
        return signInDataSource.signInUser(email, password, callback);
    }

    @Override
    public LiveData<User> signInUser(String email, String password) {
        return signInDataSource.signInUser(email, password);
    }
}
