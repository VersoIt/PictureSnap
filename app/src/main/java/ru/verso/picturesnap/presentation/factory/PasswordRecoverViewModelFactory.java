package ru.verso.picturesnap.presentation.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.SendPasswordRecoverUseCase;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PasswordRecoverViewModel;

public class PasswordRecoverViewModelFactory implements ViewModelProvider.Factory {

    private final SendPasswordRecoverUseCase sendPasswordRecoverUseCase;

    public PasswordRecoverViewModelFactory(SendPasswordRecoverUseCase sendPasswordRecoverUseCase) {
        this.sendPasswordRecoverUseCase = sendPasswordRecoverUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PasswordRecoverViewModel(sendPasswordRecoverUseCase);
    }
}
