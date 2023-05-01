package ru.verso.picturesnap.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import ru.verso.picturesnap.data.storage.room.entity.PortfolioImageEntity;
import ru.verso.picturesnap.data.storage.room.entity.ServiceProvisionEntity;
import ru.verso.picturesnap.data.storage.room.root.AppDatabase;
import ru.verso.picturesnap.domain.models.PortfolioImage;
import ru.verso.picturesnap.domain.repository.ImagesRepository;

public class ImagesRepositoryImpl implements ImagesRepository {

    private final AppDatabase appDatabase;

    public ImagesRepositoryImpl(Context context) {
        appDatabase = AppDatabase.getDatabase(context);
    }

    @Override
    public LiveData<List<PortfolioImage>> getImagesByServiceProvisionId(int id) {
        LiveData<List<PortfolioImageEntity>> workingDaysEntity = appDatabase.portfolioImageDAO().getPortfolioImagesByServiceProvisionId(id);
        return Transformations.map(
                workingDaysEntity,
                values -> values.stream().map(PortfolioImageEntity::mapToDomain).collect(Collectors.toList())
        );
    }
}
