package ru.verso.picturesnap.presentation.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.GetServicesUseCase;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotographServicesSelectionViewModel;

public class PhotographServicesSelectionViewModelFactory implements ViewModelProvider.Factory {

    private final GetServicesUseCase getServicesUseCase;

    private final LifecycleOwner viewLifeCycleOwner;

    public PhotographServicesSelectionViewModelFactory(LifecycleOwner viewLifeCycleOwner, GetServicesUseCase getServicesUseCase) {
        this.getServicesUseCase = getServicesUseCase;
        this.viewLifeCycleOwner = viewLifeCycleOwner;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PhotographServicesSelectionViewModel(viewLifeCycleOwner, getServicesUseCase);
    }
}
