package ru.verso.picturesnap.presentation.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.GetPhotographWorkingDaysUseCase;
import ru.verso.picturesnap.presentation.viewmodel.WorkingDaysViewModel;

public class WorkingDaysViewModelFactory implements ViewModelProvider.Factory {

    private final GetPhotographWorkingDaysUseCase getPhotographWorkingDaysUseCase;

    public WorkingDaysViewModelFactory(GetPhotographWorkingDaysUseCase getPhotographWorkingDaysUseCase) {
        this.getPhotographWorkingDaysUseCase = getPhotographWorkingDaysUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new WorkingDaysViewModel(getPhotographWorkingDaysUseCase);
    }
}
