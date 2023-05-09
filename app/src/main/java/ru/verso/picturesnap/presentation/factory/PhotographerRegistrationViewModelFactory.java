package ru.verso.picturesnap.presentation.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.SignInUserUseCase;
import ru.verso.picturesnap.domain.usecase.SignUpNewPhotographerUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotographerRegistrationViewModel;

public class PhotographerRegistrationViewModelFactory implements ViewModelProvider.Factory {

    private final SignUpNewPhotographerUseCase signUpNewPhotographerUseCase;

    private final SignInUserUseCase signInUserUseCase;

    private final UpdateUserDataUseCase updateUserDataUseCase;

    public PhotographerRegistrationViewModelFactory(SignUpNewPhotographerUseCase signUpNewPhotographerUseCase, SignInUserUseCase signInUserUseCase, UpdateUserDataUseCase updateUserDataUseCase) {
        this.signUpNewPhotographerUseCase = signUpNewPhotographerUseCase;
        this.signInUserUseCase = signInUserUseCase;
        this.updateUserDataUseCase = updateUserDataUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PhotographerRegistrationViewModel(signUpNewPhotographerUseCase, signInUserUseCase, updateUserDataUseCase);
    }
}
