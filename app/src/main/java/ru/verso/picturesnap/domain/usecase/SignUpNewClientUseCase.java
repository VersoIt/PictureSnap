package ru.verso.picturesnap.domain.usecase;

import androidx.lifecycle.LiveData;

import ru.verso.picturesnap.domain.models.Client;
import ru.verso.picturesnap.domain.repository.ClientSignUpRepository;
import ru.verso.picturesnap.domain.repository.SignUpFailureCallback;

public class SignUpNewClientUseCase {

    private final ClientSignUpRepository signUpClientRepository;

    public SignUpNewClientUseCase(ClientSignUpRepository signUpClientRepository) {
        this.signUpClientRepository = signUpClientRepository;
    }

    public LiveData<Client> signUpClient(Client client, String password, SignUpFailureCallback<Client> signUpCallback) {
        return signUpClientRepository.signUpClient(client, password, signUpCallback);
    }

    public String capitalize(String value) {
        if (value != null && !value.isEmpty()) {
            value = value.trim();
            value = Character.toUpperCase(value.charAt(0)) + value.substring(1).toLowerCase();
        }

        return value;
    }

    public boolean isHaveAllData(Client client, String password, String passwordConfirmation) {

        if (client.getFirstName() == null ||
                client.getLastName() == null ||
                client.getEmail() == null ||
                password == null ||
                passwordConfirmation == null)
            return false;

        return !client.getFirstName().isEmpty() &&
                !client.getLastName().isEmpty() &&
                !client.getEmail().isEmpty() &&
                !password.isEmpty() &&
                !passwordConfirmation.isEmpty();
    }

    public boolean isValidName(String name) {
        if (name != null)
            return name.matches("^\\p{L}+$");

        return false;
    }

    public boolean isEmailMatch(String email) {
        if (email != null) {
            final String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
            return email.matches(emailRegex);
        }

        return false;
    }

}
