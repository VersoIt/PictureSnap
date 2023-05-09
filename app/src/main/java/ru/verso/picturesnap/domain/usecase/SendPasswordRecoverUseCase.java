package ru.verso.picturesnap.domain.usecase;

import ru.verso.picturesnap.domain.repository.PasswordRecoverRepository;
import ru.verso.picturesnap.domain.repository.PasswordResetCallback;

public class SendPasswordRecoverUseCase {

    private final PasswordRecoverRepository passwordRecoverRepository;

    public SendPasswordRecoverUseCase(PasswordRecoverRepository passwordRecoverRepository) {
        this.passwordRecoverRepository = passwordRecoverRepository;
    }

    public void sendPasswordRecoverTo(String email, PasswordResetCallback passwordResetCallback) {
        passwordRecoverRepository.sendPasswordResetTo(email, passwordResetCallback);
    }

    public boolean isEmailCorrect(String email) {
        if (email != null) {
            final String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
            return email.matches(emailRegex);
        }

        return false;
    }
}
