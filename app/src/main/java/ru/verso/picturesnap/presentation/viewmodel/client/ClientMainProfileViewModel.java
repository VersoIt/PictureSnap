package ru.verso.picturesnap.presentation.viewmodel.client;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import ru.verso.picturesnap.domain.models.Client;
import ru.verso.picturesnap.domain.models.Location;
import ru.verso.picturesnap.domain.usecase.GetClientDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;

public class ClientMainProfileViewModel extends ViewModel {

    private final LiveData<Client> client;

    private final GetUserDataUseCase getUserDataUseCase;

    private final GetClientDataUseCase getClientDataUseCase;

    private final String clientId;

    public ClientMainProfileViewModel(GetClientDataUseCase getClientDataUseCase, GetUserDataUseCase getUserDataUseCase) {
        clientId = getUserDataUseCase.getUserId();
        client = getClientDataUseCase.getClientById(clientId);
        this.getUserDataUseCase = getUserDataUseCase;
        this.getClientDataUseCase = getClientDataUseCase;
    }

    public LiveData<String> getClientName() {
        return Transformations.map(client, c -> String.format("%s %s", c.getFirstName(), c.getLastName()));
    }

    public LiveData<String> getClientEmail() {
        return Transformations.map(client, Client::getEmail);
    }

    public Location getCityLocation() {
        return getUserDataUseCase.getLocation();
    }

    public LiveData<String> getAvatarPath() {
        return Transformations.map(client, Client::getImagePath);
    }

    public void signOut() {
        getUserDataUseCase.signOut();
    }

    public void updateAvatar(Uri uri) {
        getClientDataUseCase.updateAvatar(clientId, uri);
    }
}
