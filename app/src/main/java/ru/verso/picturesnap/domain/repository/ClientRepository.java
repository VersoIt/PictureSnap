package ru.verso.picturesnap.domain.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.Client;
import ru.verso.picturesnap.domain.models.PhotographerService;

public interface ClientRepository {

    LiveData<Client> getClientById(String id);
}
