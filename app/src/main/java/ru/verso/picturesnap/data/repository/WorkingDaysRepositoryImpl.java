package ru.verso.picturesnap.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import ru.verso.picturesnap.data.storage.room.entity.WorkingDayEntity;
import ru.verso.picturesnap.data.storage.room.root.AppDatabase;
import ru.verso.picturesnap.domain.models.WorkingDay;
import ru.verso.picturesnap.domain.repository.WorkingDaysRepository;

public class WorkingDaysRepositoryImpl implements WorkingDaysRepository {

    private final AppDatabase appDatabase;

    public WorkingDaysRepositoryImpl(Context context) {
        appDatabase = AppDatabase.getDatabase(context);
    }

    @Override
    public LiveData<List<WorkingDay>> getPhotographWorkingDaysByPhotographId(int photographId) {
        LiveData<List<WorkingDayEntity>> workingDaysEntity = appDatabase.workingDaysDAO().getWorkingDaysByPhotograph(photographId);
        return Transformations.map(
                workingDaysEntity,
                values -> values.stream().map(WorkingDayEntity::mapToDomain).collect(Collectors.toList())
        );
    }
}
