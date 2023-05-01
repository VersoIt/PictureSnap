package ru.verso.picturesnap.presentation.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;
import ru.verso.picturesnap.presentation.viewmodel.ClientActivityViewModel;

public class ClientActivityViewModelFactory implements ViewModelProvider.Factory {

    private final UpdateUserDataUseCase updateUserDataUseCase;
    private final GetUserDataUseCase getUserDataUseCase;

    public ClientActivityViewModelFactory(UpdateUserDataUseCase updateUserDataUseCase,
                                          GetUserDataUseCase getUserDataUseCase) {

        this.updateUserDataUseCase = updateUserDataUseCase;
        this.getUserDataUseCase = getUserDataUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ClientActivityViewModel(updateUserDataUseCase,
                getUserDataUseCase);
    }
}
