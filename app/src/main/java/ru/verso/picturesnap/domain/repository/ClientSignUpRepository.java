package ru.verso.picturesnap.domain.repository;

import androidx.lifecycle.LiveData;

import ru.verso.picturesnap.domain.models.Client;

public interface ClientSignUpRepository {

    LiveData<Client> signUpClient(Client client, String password, SignUpFailureCallback<Client> signUpCallback);

    LiveData<Client> signUpClient(Client client, String password);
}
