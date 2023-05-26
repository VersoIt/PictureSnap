package ru.verso.picturesnap.data.storage.datasources;

import android.net.Uri;

import androidx.lifecycle.LiveData;

import ru.verso.picturesnap.domain.models.Client;

public interface ClientDataSource {

    LiveData<Client> getClientById(String id);

    void updateAvatar(String clientId, Uri uri);
}
