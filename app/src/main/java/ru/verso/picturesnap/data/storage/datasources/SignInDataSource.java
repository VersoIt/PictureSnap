package ru.verso.picturesnap.data.storage.datasources;

import androidx.lifecycle.LiveData;

import ru.verso.picturesnap.domain.models.User;
import ru.verso.picturesnap.domain.repository.SignInCallback;

public interface SignInDataSource {

    LiveData<User> signInUser(String email, String password, SignInCallback<User> callback);

    LiveData<User> signInUser(String email, String password);
}
