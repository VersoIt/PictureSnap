package ru.verso.picturesnap.domain.usecase;

import androidx.lifecycle.LiveData;

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
}
