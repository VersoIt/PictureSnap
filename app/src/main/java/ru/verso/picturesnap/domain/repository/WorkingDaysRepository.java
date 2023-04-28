package ru.verso.picturesnap.domain.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.WorkingDay;

public interface WorkingDaysRepository {

    LiveData<List<WorkingDay>> getPhotographWorkingDaysByPhotographId(int id);
}
