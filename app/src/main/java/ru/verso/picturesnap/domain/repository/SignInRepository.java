package ru.verso.picturesnap.domain.repository;

import androidx.lifecycle.LiveData;

import ru.verso.picturesnap.domain.models.User;

public interface SignInRepository {

    LiveData<User> signInUser(String email, String password, SignInCallback<User> callback);

    LiveData<User> signInUser(String email, String password);
}