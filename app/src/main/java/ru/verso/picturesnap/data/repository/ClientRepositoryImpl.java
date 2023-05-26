package ru.verso.picturesnap.data.repository;

import android.net.Uri;

import androidx.lifecycle.LiveData;

import ru.verso.picturesnap.data.storage.datasources.ClientDataSource;
import ru.verso.picturesnap.domain.models.Client;
import ru.verso.picturesnap.domain.repository.ClientRepository;

public class ClientRepositoryImpl implements ClientRepository {

    private final ClientDataSource clientDataSource;

    public ClientRepositoryImpl(ClientDataSource clientDataSource) {
        this.clientDataSource = clientDataSource;
    }

    @Override
    public LiveData<Client> getClientById(String id) {
        return clientDataSource.getClientById(id);
    }

    @Override
    public void updateAvatar(String clientId, Uri uri) {
        clientDataSource.updateAvatar(clientId, uri);
    }
}
