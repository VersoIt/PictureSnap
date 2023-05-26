package ru.verso.picturesnap.data.repository;

import androidx.lifecycle.LiveData;

import ru.verso.picturesnap.data.storage.datasources.ClientSignUpDataSource;
import ru.verso.picturesnap.domain.models.Client;
import ru.verso.picturesnap.domain.repository.ClientSignUpRepository;
import ru.verso.picturesnap.domain.repository.SignUpFailureCallback;

public class ClientSignUpRepositoryImpl implements ClientSignUpRepository {

    private final ClientSignUpDataSource clientSignUpDataSource;

    public ClientSignUpRepositoryImpl(ClientSignUpDataSource clientSignUpDataSource) {
        this.clientSignUpDataSource = clientSignUpDataSource;
    }

    @Override
    public LiveData<Client> signUpClient(Client client, String password) {
        return clientSignUpDataSource.signUpClient(client, password);
    }

    @Override
    public LiveData<Client> signUpClient(Client client, String password, SignUpFailureCallback<Client> signUpCallback) {
        return clientSignUpDataSource.signUpClient(client, password, signUpCallback);
    }
}
