package ru.verso.picturesnap.presentation.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.SignUpNewPhotographUseCase;
import ru.verso.picturesnap.presentation.viewmodel.unregistered.PhotographRegistrationViewModel;

public class PhotographRegistrationViewModelFactory implements ViewModelProvider.Factory {

    private final SignUpNewPhotographUseCase signUpNewPhotographUseCase;

    public PhotographRegistrationViewModelFactory(SignUpNewPhotographUseCase signUpNewPhotographUseCase) {
        this.signUpNewPhotographUseCase = signUpNewPhotographUseCase;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PhotographRegistrationViewModel(signUpNewPhotographUseCase);
    }
}
