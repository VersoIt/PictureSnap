package ru.verso.picturesnap.presentation.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.GetPhotographDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;
import ru.verso.picturesnap.presentation.viewmodel.UnregisteredMainViewModel;

public class UnregisteredMainViewModelFactory implements ViewModelProvider.Factory {

    private final GetPhotographDataUseCase getPhotographDataUseCase;

    private final GetUserDataUseCase getUserDataUseCase;

    private final UpdateUserDataUseCase updateUserDataUseCase;

    public UnregisteredMainViewModelFactory(GetPhotographDataUseCase getPhotographDataUseCase,
                                            GetUserDataUseCase getUserDataUseCase,
                                            UpdateUserDataUseCase updateUserDataUseCase) {
        this.getPhotographDataUseCase = getPhotographDataUseCase;
        this.getUserDataUseCase = getUserDataUseCase;
        this.updateUserDataUseCase = updateUserDataUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UnregisteredMainViewModel(getPhotographDataUseCase, getUserDataUseCase, updateUserDataUseCase);
    }
}
