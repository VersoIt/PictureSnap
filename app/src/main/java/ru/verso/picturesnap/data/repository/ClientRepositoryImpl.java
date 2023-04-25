package ru.verso.picturesnap.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import ru.verso.picturesnap.data.storage.room.dao.PhotographServiceDAO;
import ru.verso.picturesnap.data.storage.room.entity.PhotographServiceEntity;
import ru.verso.picturesnap.data.storage.room.root.AppDatabase;
import ru.verso.picturesnap.domain.models.PhotographService;
import ru.verso.picturesnap.domain.repository.ClientRepository;

public class ClientRepositoryImpl implements ClientRepository {

    private final LiveData<List<PhotographServiceEntity>> photographServiceEntities;

    public ClientRepositoryImpl(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);

        PhotographServiceDAO photographServiceDAO = database.photographServiceDAO();
        photographServiceEntities = photographServiceDAO.getAllServices();
    }

    @Override
    public LiveData<List<PhotographService>> getPhotographServices() {
        return mapPhotographServiceEntityToDomain(photographServiceEntities);
    }

    private LiveData<List<PhotographService>> mapPhotographServiceEntityToDomain(LiveData<List<PhotographServiceEntity>> entity) {
        return Transformations.map(
                entity,
                (values) -> values.stream().map(PhotographServiceEntity::mapToDomain).collect(Collectors.toList())
        );
    }
}
