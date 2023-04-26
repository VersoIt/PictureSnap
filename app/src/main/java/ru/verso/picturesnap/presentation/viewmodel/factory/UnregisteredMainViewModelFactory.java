package ru.verso.picturesnap.presentation.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.repository.ClientRepository;
import ru.verso.picturesnap.domain.repository.UserLocationRepository;
import ru.verso.picturesnap.presentation.viewmodel.UnregisteredMainViewModel;

public class UnregisteredMainViewModelFactory implements ViewModelProvider.Factory {

    private final ClientRepository clientRepository;

    private final UserLocationRepository locationRepository;

    public UnregisteredMainViewModelFactory(ClientRepository clientRepository, UserLocationRepository locationRepository) {
        this.clientRepository = clientRepository;
        this.locationRepository = locationRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UnregisteredMainViewModel(clientRepository, locationRepository);
    }
}
