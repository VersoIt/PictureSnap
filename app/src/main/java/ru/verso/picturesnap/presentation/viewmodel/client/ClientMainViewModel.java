package ru.verso.picturesnap.presentation.viewmodel.client;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.function.Consumer;

import ru.verso.picturesnap.domain.models.Client;
import ru.verso.picturesnap.domain.models.Record;
import ru.verso.picturesnap.domain.usecase.GetClientDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetClientRecordsUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;

public class ClientMainViewModel extends ViewModel {

    private final LiveData<Client> client;

    private final GetClientRecordsUseCase getClientRecordsUseCase;

    public ClientMainViewModel(GetUserDataUseCase getUserDataUseCase, GetClientDataUseCase getClientDataUseCase, GetClientRecordsUseCase getClientRecordsUseCase) {
        String id = getUserDataUseCase.getUserId();
        client = getClientDataUseCase.getClientById(id);
        this.getClientRecordsUseCase = getClientRecordsUseCase;
    }

    public LiveData<String> getClientEmail() {
        return Transformations.map(client, Client::getEmail);
    }

    public LiveData<String> getClientName() {
        return Transformations.map(client, c -> String.format("%s %s", c.getFirstName(), c.getLastName()));
    }

    public LiveData<String> getClientAvatar() {
        return Transformations.map(client, Client::getImagePath);
    }

    public void getClientRecords(String clientId, Consumer<List<Record>> callback) {
        getClientRecordsUseCase.getClientRecords(clientId, callback);
    }

    public LiveData<String> getClientId() {
        return Transformations.map(client, Client::getId);
    }
}