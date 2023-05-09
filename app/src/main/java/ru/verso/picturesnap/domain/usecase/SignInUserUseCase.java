package ru.verso.picturesnap.domain.usecase;

import androidx.lifecycle.LiveData;

import ru.verso.picturesnap.domain.models.User;
import ru.verso.picturesnap.domain.repository.SignInCallback;
import ru.verso.picturesnap.domain.repository.SignInRepository;

public class SignInUserUseCase {

    private final SignInRepository signInRepository;

    public SignInUserUseCase(SignInRepository signInRepository) {
        this.signInRepository = signInRepository;
    }

    public LiveData<User> signInUser(String email, String password) {
        return signInRepository.signInUser(email, password);
    }

    public LiveData<User> signInUser(String email, String password, SignInCallback<User> signInCallback) {
        return signInRepository.signInUser(email, password, signInCallback);
    }

    public boolean isHaveAllData(String email, String password) {

        if (email == null || password == null)
                return false;

        return !email.isEmpty() && !password.isEmpty();
    }

    public boolean isEmailMatch(String email) {
        if (email == null)
            return false;

        final String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
}
