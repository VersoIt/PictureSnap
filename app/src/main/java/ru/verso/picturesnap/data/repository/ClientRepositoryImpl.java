package ru.verso.picturesnap.data.repository;

import android.app.Application;

import ru.verso.picturesnap.data.storage.room.root.AppDatabase;
import ru.verso.picturesnap.domain.repository.ClientRepository;

public class ClientRepositoryImpl implements ClientRepository {

    public ClientRepositoryImpl(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
    }
}
