package ru.verso.picturesnap.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import ru.verso.picturesnap.domain.models.Client;
import ru.verso.picturesnap.domain.usecase.GetClientDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;

public class ClientMainViewModel extends ViewModel {

    private final LiveData<Client> client;

    public ClientMainViewModel(GetUserDataUseCase getUserDataUseCase, GetClientDataUseCase getClientDataUseCase) {
        String id = getUserDataUseCase.getUserId();
        client = getClientDataUseCase.getClientById(id);
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
}