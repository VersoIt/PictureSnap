package ru.verso.picturesnap.presentation.viewmodel.unregistered;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.verso.picturesnap.domain.repository.PasswordResetCallback;
import ru.verso.picturesnap.domain.usecase.SendPasswordRecoverUseCase;

public class PasswordRecoverViewModel extends ViewModel {

    private final SendPasswordRecoverUseCase sendPasswordRecoverUseCase;

    private final MutableLiveData<String> email;

    public PasswordRecoverViewModel(SendPasswordRecoverUseCase sendPasswordRecoverUseCase) {
        this.sendPasswordRecoverUseCase = sendPasswordRecoverUseCase;
        this.email = new MutableLiveData<>("");
    }

    private boolean isEmailCorrect() {
        return sendPasswordRecoverUseCase.isEmailCorrect(email.getValue());
    }

    public boolean sendPasswordReset(PasswordResetCallback callback) {
        if (isEmailCorrect()) {
            sendPasswordRecoverUseCase.sendPasswordRecoverTo(email.getValue(), callback);
            return true;
        }

        return false;
    }

    public void clearEmail() {
        email.setValue("");
    }

    public void setEmail(String email) {
        this.email.setValue(email.trim());
    }
}
