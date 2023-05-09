package ru.verso.picturesnap.presentation.viewmodel.unregistered;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.verso.picturesnap.domain.models.Client;
import ru.verso.picturesnap.domain.models.User;
import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.domain.repository.SignInCallback;
import ru.verso.picturesnap.domain.usecase.SignInUserUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<String> email;

    private final MutableLiveData<String> password;

    private final SignInUserUseCase signInUserUseCase;

    private final UpdateUserDataUseCase updateUserDataUseCase;

    private static final String PHOTOGRAPHER = "photographer";

    private static final String CLIENT = "client";

    public LoginViewModel(SignInUserUseCase signInUserUseCase, UpdateUserDataUseCase updateUserDataUseCase) {
        this.signInUserUseCase = signInUserUseCase;
        this.email = new MutableLiveData<>("");
        this.password = new MutableLiveData<>("");
        this.updateUserDataUseCase = updateUserDataUseCase;
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public LiveData<String> getPassword() {
        return password;
    }

    public boolean isHaveAllData() {
        return signInUserUseCase.isHaveAllData(email.getValue(), password.getValue());
    }

    public boolean isEmailMatch() {
        return signInUserUseCase.isEmailMatch(email.getValue());
    }

    public LiveData<User> signIn() {
        return signInUserUseCase.signInUser(getEmail().getValue(), getPassword().getValue());
    }

    public LiveData<User> signIn(SignInCallback<User> signInCallback) {
        return signInUserUseCase.signInUser(getEmail().getValue(), getPassword().getValue(), signInCallback);
    }

    public void saveRoleInLocal(User user) {
        if (user.getRole().equals(PHOTOGRAPHER))
            updateUserDataUseCase.setRole(RoleRepository.Role.PHOTOGRAPH);
        else
            updateUserDataUseCase.setRole(RoleRepository.Role.CLIENT);
    }
}
