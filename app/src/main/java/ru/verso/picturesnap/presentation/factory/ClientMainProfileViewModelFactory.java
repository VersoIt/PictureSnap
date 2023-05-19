package ru.verso.picturesnap.presentation.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.GetClientDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.presentation.viewmodel.client.ClientMainProfileViewModel;

public class ClientMainProfileViewModelFactory implements ViewModelProvider.Factory {

    private final GetUserDataUseCase getUserDataUseCase;

    private final GetClientDataUseCase getClientDataUseCase;

    public ClientMainProfileViewModelFactory(GetUserDataUseCase getUserDataUseCase, GetClientDataUseCase getClientDataUseCase) {
        this.getClientDataUseCase = getClientDataUseCase;
        this.getUserDataUseCase = getUserDataUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ClientMainProfileViewModel(getClientDataUseCase, getUserDataUseCase);
    }
}
