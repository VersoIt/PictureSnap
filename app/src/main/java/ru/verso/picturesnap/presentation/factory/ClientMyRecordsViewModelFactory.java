package ru.verso.picturesnap.presentation.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.GetClientRecordsUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.presentation.viewmodel.client.ClientMyRecordsViewModel;

public class ClientMyRecordsViewModelFactory implements ViewModelProvider.Factory {

    private final GetPhotographerDataUseCase getPhotographerDataUseCase;
    private final GetClientRecordsUseCase getClientRecordsUseCase;
    private final GetUserDataUseCase getUserDataUseCase;

    public ClientMyRecordsViewModelFactory(GetPhotographerDataUseCase getPhotographerDataUseCase,
                                           GetClientRecordsUseCase getClientRecordsUseCase,
                                           GetUserDataUseCase getUserDataUseCase) {

        this.getPhotographerDataUseCase = getPhotographerDataUseCase;
        this.getClientRecordsUseCase = getClientRecordsUseCase;
        this.getUserDataUseCase = getUserDataUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ClientMyRecordsViewModel(getPhotographerDataUseCase, getClientRecordsUseCase, getUserDataUseCase);
    }
}
