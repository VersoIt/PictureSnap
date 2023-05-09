package ru.verso.picturesnap.presentation.viewmodel.unregistered;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import ru.verso.picturesnap.domain.models.Client;
import ru.verso.picturesnap.domain.models.User;
import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.domain.repository.SignUpFailureCallback;
import ru.verso.picturesnap.domain.usecase.SignInUserUseCase;
import ru.verso.picturesnap.domain.usecase.SignUpNewClientUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;
import ru.verso.picturesnap.presentation.app.PictureSnapApp;

public class ClientRegistrationViewModel extends ViewModel {

    private final MutableLiveData<String> firstName;
    private final MutableLiveData<String> lastName;
    private final MutableLiveData<String> email;
    private final MutableLiveData<String> password;
    private final MutableLiveData<String> passwordConfirmation;

    private final SignUpNewClientUseCase signUpNewClientUseCase;

    private final SignInUserUseCase signInUserUseCase;

    private final UpdateUserDataUseCase updateUserDataUseCase;

    public ClientRegistrationViewModel(SignUpNewClientUseCase signUpNewClientUseCase,
                                       SignInUserUseCase signInUserUseCase,
                                       UpdateUserDataUseCase updateUserDataUseCase) {

        this.signUpNewClientUseCase = signUpNewClientUseCase;
        this.signInUserUseCase = signInUserUseCase;
        this.updateUserDataUseCase = updateUserDataUseCase;

        this.firstName = new MutableLiveData<>("");
        this.lastName = new MutableLiveData<>("");
        this.email = new MutableLiveData<>("");
        this.password = new MutableLiveData<>("");
        this.passwordConfirmation = new MutableLiveData<>("");
    }

    public void setFirstName(String firstName) {
        this.firstName.setValue(signUpNewClientUseCase.capitalize(firstName));
    }

    public void setLastName(String lastName) {
        this.lastName.setValue(signUpNewClientUseCase.capitalize(lastName));
    }

    public void setEmail(String email) {
        this.email.setValue(email);
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }

    public void setPasswordConfirmation(String confirmation) {
        this.passwordConfirmation.setValue(confirmation);
    }

    public MutableLiveData<String> getFirstName() {
        return firstName;
    }

    public MutableLiveData<String> getLastName() {
        return lastName;
    }

    public MutableLiveData<String> getEmail() {
        return email;
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public MutableLiveData<String> getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public boolean isPasswordsMatch() {

        if (password.getValue() == null || passwordConfirmation.getValue() == null)
            return false;

        return password.getValue().equals(passwordConfirmation.getValue());
    }

    public boolean isValidFirstName() {
        return signUpNewClientUseCase.isValidName(firstName.getValue());
    }

    public boolean isValidLastName() {
        return signUpNewClientUseCase.isValidName(lastName.getValue());
    }

    public boolean isValidPassword() {
        return Objects.requireNonNull(password.getValue()).length() >= PictureSnapApp.PASSWORD_MIN_LENGTH;
    }

    public boolean isHaveAllData() {
        Client client = new Client("", firstName.getValue(), lastName.getValue(), email.getValue(), "");
        return signUpNewClientUseCase.isHaveAllData(client, password.getValue(), passwordConfirmation.getValue());
    }

    public boolean isEmailMatch() {
        return signUpNewClientUseCase.isEmailMatch(email.getValue());
    }

    public LiveData<Client> signUpClient(SignUpFailureCallback<Client> signUpCallback) {

        Client client = new Client("", firstName.getValue(), lastName.getValue(), email.getValue(), "");
        return signUpNewClientUseCase.signUpClient(client, password.getValue(), signUpCallback);
    }

    public LiveData<User> signInClient() {
        updateUserDataUseCase.setRole(RoleRepository.Role.CLIENT);
        return signInUserUseCase.signInUser(getEmail().getValue(), getPassword().getValue());
    }
}
