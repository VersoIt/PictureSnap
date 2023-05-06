package ru.verso.picturesnap.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

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
        return null;
    }
}
