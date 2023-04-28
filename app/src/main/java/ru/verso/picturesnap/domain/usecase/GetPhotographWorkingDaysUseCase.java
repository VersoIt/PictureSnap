package ru.verso.picturesnap.domain.usecase;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.WorkingDay;
import ru.verso.picturesnap.domain.repository.WorkingDaysRepository;

public class GetPhotographWorkingDaysUseCase {

    private final WorkingDaysRepository workingDaysRepository;

    public GetPhotographWorkingDaysUseCase(WorkingDaysRepository workingDaysRepository) {
        this.workingDaysRepository = workingDaysRepository;
    }

    public LiveData<List<WorkingDay>> getPhotographWorkingDaysByPhotographId(int id) {
        return workingDaysRepository.getPhotographWorkingDaysByPhotographId(id);
    }
}
