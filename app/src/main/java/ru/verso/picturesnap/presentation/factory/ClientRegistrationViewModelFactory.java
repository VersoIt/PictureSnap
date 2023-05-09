package ru.verso.picturesnap.presentation.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.SignInUserUseCase;
import ru.verso.picturesnap.domain.usecase.SignUpNewClientUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.ClientRegistrationViewModel;

public class ClientRegistrationViewModelFactory implements ViewModelProvider.Factory {

    private final SignUpNewClientUseCase signUpNewClientUseCase;

    private final SignInUserUseCase signInUserUseCase;

    private final UpdateUserDataUseCase updateUserDataUseCase;

    public ClientRegistrationViewModelFactory(SignUpNewClientUseCase signUpNewClientUseCase,
                                              SignInUserUseCase signInUserUseCase,
                                              UpdateUserDataUseCase updateUserDataUseCase) {
        this.signInUserUseCase = signInUserUseCase;
        this.signUpNewClientUseCase = signUpNewClientUseCase;
        this.updateUserDataUseCase = updateUserDataUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ClientRegistrationViewModel(signUpNewClientUseCase, signInUserUseCase, updateUserDataUseCase);
    }
}
