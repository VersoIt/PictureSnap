package ru.verso.picturesnap.data.storage.datasources;

import androidx.lifecycle.LiveData;

import ru.verso.picturesnap.domain.models.Client;
import ru.verso.picturesnap.domain.repository.SignUpFailureCallback;

public interface ClientSignUpDataSource {

    LiveData<Client> signUpClient(Client client, String password);

    LiveData<Client> signUpClient(Client client, String password, SignUpFailureCallback<Client> signUpCallback);
}
