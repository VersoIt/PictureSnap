package ru.verso.picturesnap.domain.usecase;

import android.net.Uri;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.FirebaseDatabase;

import ru.verso.picturesnap.domain.models.Client;
import ru.verso.picturesnap.domain.repository.ClientRepository;

public class GetClientDataUseCase {

    private final ClientRepository clientRepository;

    public GetClientDataUseCase(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public LiveData<Client> getClientById(String id) {
        return clientRepository.getClientById(id);
    }

    public void updateAvatar(String clientId, Uri uri) {
        clientRepository.updateAvatar(clientId, uri);
    }
}
