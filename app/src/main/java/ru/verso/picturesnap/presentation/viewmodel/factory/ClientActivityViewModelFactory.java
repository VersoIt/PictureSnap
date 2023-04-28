package ru.verso.picturesnap.presentation.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.GetPhotographDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdatePhotographDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;
import ru.verso.picturesnap.presentation.viewmodel.ClientActivityViewModel;

public class ClientActivityViewModelFactory implements ViewModelProvider.Factory {

    private final UpdateUserDataUseCase updateUserDataUseCase;
    private final UpdatePhotographDataUseCase updatePhotographDataUseCase;
    private final GetUserDataUseCase getUserDataUseCase;
    private final GetPhotographDataUseCase getPhotographDataUseCase;

    public ClientActivityViewModelFactory(UpdateUserDataUseCase updateUserDataUseCase,
                                          GetUserDataUseCase getUserDataUseCase,
                                          UpdatePhotographDataUseCase updatePhotographDataUseCase,
                                          GetPhotographDataUseCase getPhotographDataUseCase) {

        this.updateUserDataUseCase = updateUserDataUseCase;
        this.updatePhotographDataUseCase = updatePhotographDataUseCase;
        this.getUserDataUseCase = getUserDataUseCase;
        this.getPhotographDataUseCase = getPhotographDataUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ClientActivityViewModel(updateUserDataUseCase,
                getUserDataUseCase,
                updatePhotographDataUseCase,
                getPhotographDataUseCase);
    }
}
