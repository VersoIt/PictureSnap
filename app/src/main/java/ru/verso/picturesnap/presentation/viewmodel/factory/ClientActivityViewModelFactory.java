package ru.verso.picturesnap.presentation.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.repository.FirstTimeWentRepository;
import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.domain.repository.UserLocationRepository;
import ru.verso.picturesnap.domain.usecase.OperationPhotographDataUseCase;
import ru.verso.picturesnap.domain.usecase.OperationUserDataUseCase;
import ru.verso.picturesnap.presentation.viewmodel.ClientActivityViewModel;

public class ClientActivityViewModelFactory implements ViewModelProvider.Factory {

    private final OperationUserDataUseCase operationUserDataUseCase;
    private final OperationPhotographDataUseCase operationPhotographDataUseCase;

    public ClientActivityViewModelFactory(OperationUserDataUseCase operationUserDataUseCase,
                                          OperationPhotographDataUseCase operationPhotographDataUseCase) {

        this.operationUserDataUseCase = operationUserDataUseCase;
        this.operationPhotographDataUseCase = operationPhotographDataUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ClientActivityViewModel(operationUserDataUseCase, operationPhotographDataUseCase);
    }
}
