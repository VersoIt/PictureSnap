package ru.verso.picturesnap.presentation.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.GetClientDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetClientRecordsUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.presentation.viewmodel.client.ClientMainViewModel;

public class ClientMainViewModelFactory implements ViewModelProvider.Factory {

    private final GetUserDataUseCase getUserDataUseCase;
    private final GetClientDataUseCase getClientDataUseCase;

    private final GetClientRecordsUseCase getClientRecordsUseCase;

    public ClientMainViewModelFactory(GetUserDataUseCase getUserDataUseCase, GetClientDataUseCase getClientDataUseCase, GetClientRecordsUseCase getClientRecordsUseCase) {
        this.getClientDataUseCase = getClientDataUseCase;
        this.getUserDataUseCase = getUserDataUseCase;
        this.getClientRecordsUseCase = getClientRecordsUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ClientMainViewModel(getUserDataUseCase, getClientDataUseCase, getClientRecordsUseCase);
    }
}
