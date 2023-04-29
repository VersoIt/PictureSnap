package ru.verso.picturesnap.presentation.activity.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.verso.picturesnap.domain.models.WorkingDay;
import ru.verso.picturesnap.domain.usecase.GetPhotographWorkingDaysUseCase;

public class WorkingDaysViewModel extends ViewModel {

    private int photographId;

    private final GetPhotographWorkingDaysUseCase getPhotographWorkingDaysUseCase;

    public WorkingDaysViewModel(GetPhotographWorkingDaysUseCase getPhotographWorkingDaysUseCase) {
        this.getPhotographWorkingDaysUseCase = getPhotographWorkingDaysUseCase;
    }

    public void putPhotographId(int id) {
        this.photographId = id;
    }

    public LiveData<List<WorkingDay>> getWorkingDays() {
        return getPhotographWorkingDaysUseCase.getPhotographWorkingDaysByPhotographId(photographId);
    }
}
