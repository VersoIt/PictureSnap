package ru.verso.picturesnap.presentation.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.GetPhotographerServiceByIdUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographersByServiceIdUseCase;
import ru.verso.picturesnap.presentation.viewmodel.client.ClientPhotographersOfSelectedServiceViewModel;

public class ClientRecordsViewModelFactory implements ViewModelProvider.Factory {

    private final GetPhotographersByServiceIdUseCase getPhotographersByServiceIdUseCase;

    private final GetPhotographerServiceByIdUseCase getPhotographerServiceByIdUseCase;

    public ClientRecordsViewModelFactory(GetPhotographersByServiceIdUseCase getPhotographersByServiceIdUseCase, GetPhotographerServiceByIdUseCase getPhotographerServiceByIdUseCase) {
        this.getPhotographersByServiceIdUseCase = getPhotographersByServiceIdUseCase;
        this.getPhotographerServiceByIdUseCase = getPhotographerServiceByIdUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ClientPhotographersOfSelectedServiceViewModel(getPhotographersByServiceIdUseCase, getPhotographerServiceByIdUseCase);
    }
}
