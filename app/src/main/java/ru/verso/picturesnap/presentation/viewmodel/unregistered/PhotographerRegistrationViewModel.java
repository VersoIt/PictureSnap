package ru.verso.picturesnap.presentation.viewmodel.unregistered;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ru.verso.picturesnap.domain.models.Location;
import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.domain.models.User;
import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.domain.repository.SignUpFailureCallback;
import ru.verso.picturesnap.domain.usecase.SignInUserUseCase;
import ru.verso.picturesnap.domain.usecase.SignUpNewPhotographerUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateUserDataUseCase;
import ru.verso.picturesnap.presentation.app.PictureSnapApp;

public class PhotographerRegistrationViewModel extends ViewModel {

    private final MutableLiveData<String> firstName;
    private final MutableLiveData<String> lastName;
    private final MutableLiveData<String> email;
    private final MutableLiveData<String> password;
    private final MutableLiveData<String> phoneNumber;
    private final MutableLiveData<String> aboutText;
    private final MutableLiveData<String> passwordConfirmation;

    private final MutableLiveData<Location> photoSessionLocation;

    private final MutableLiveData<Integer> experience;

    private final MutableLiveData<List<PhotographerPresentationService>> services;

    private final SignUpNewPhotographerUseCase signUpNewPhotographerUseCase;

    private final SignInUserUseCase signInUserUseCase;

    private final UpdateUserDataUseCase updateUserDataUseCase;

    public PhotographerRegistrationViewModel(SignUpNewPhotographerUseCase signUpNewPhotographerUseCase,
                                             SignInUserUseCase signInUserUseCase,
                                             UpdateUserDataUseCase updateUserDataUseCase) {

        this.signUpNewPhotographerUseCase = signUpNewPhotographerUseCase;
        this.signInUserUseCase = signInUserUseCase;
        this.updateUserDataUseCase = updateUserDataUseCase;

        firstName = new MutableLiveData<>("");
        lastName = new MutableLiveData<>("");
        email = new MutableLiveData<>("");
        photoSessionLocation = new MutableLiveData<>();
        phoneNumber = new MutableLiveData<>("");
        experience = new MutableLiveData<>();
        aboutText = new MutableLiveData<>("");
        services = new MutableLiveData<>();
        password = new MutableLiveData<>();
        passwordConfirmation = new MutableLiveData<>();
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }

    public LiveData<String> getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String confirmation) {
        passwordConfirmation.setValue(confirmation);
    }

    public boolean isPasswordsMatch() {
        return Objects.equals(password.getValue(), passwordConfirmation.getValue());
    }

    public LiveData<String> getPassword() {
        return password;
    }

    public LiveData<String> getFirstName() {
        return firstName;
    }

    public void setFirstName(String value) {
        firstName.setValue(capitalize(value));
    }

    private String capitalize(String value) {
        if (value != null && !value.isEmpty()) {
            value = value.trim();
            value = Character.toUpperCase(value.charAt(0)) + value.substring(1).toLowerCase();
        }

        return value;
    }

    public LiveData<String> getLastName() {
        return lastName;
    }

    public void setLastName(String value) {
        lastName.setValue(capitalize(value));
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public void setEmail(String value) {
        email.setValue(value.trim());
    }

    public LiveData<Location> getPhotoSessionLocation() {
        return photoSessionLocation;
    }

    public void setPhotoSessionLocation(Location value) {
        photoSessionLocation.setValue(value);
    }

    public LiveData<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String value) {
        phoneNumber.setValue(value.trim());
    }

    public LiveData<Integer> getExperience() {
        return experience;
    }

    public void setExperience(int value) {
        experience.setValue(value);
    }

    public LiveData<String> getAboutText() {
        return aboutText;
    }

    public LiveData<List<PhotographerPresentationService>> getServices() {
        return services;
    }

    public void setServices(List<PhotographerPresentationService> services) {
        this.services.setValue(services.stream().filter(s -> s.getCost() > 0).collect(Collectors.toList()));
    }

    public LiveData<Photographer> signUpPhotographer(SignUpFailureCallback<Photographer> signUpCallback) {

        Photographer.Builder photographerBuilder = new Photographer.Builder();
        photographerBuilder.setName(getFirstName().getValue(), getLastName().getValue());
        photographerBuilder.setEmail(getEmail().getValue());
        photographerBuilder.setLocation(Objects.requireNonNull(photoSessionLocation.getValue()).getLatitude(), photoSessionLocation.getValue().getLongitude());
        photographerBuilder.setPhoneNumber(phoneNumber.getValue());
        photographerBuilder.setExperience(experience.getValue());
        photographerBuilder.setDescription(aboutText.getValue());
        photographerBuilder.setAvatarPath("");

        return signUpNewPhotographerUseCase.signUpPhotographer(photographerBuilder.create(), services.getValue(), password.getValue(), signUpCallback);
    }

    public LiveData<User> signInPhotograph() {
        updateUserDataUseCase.setRole(RoleRepository.Role.PHOTOGRAPH);
        return signInUserUseCase.signInUser(getEmail().getValue(), getPassword().getValue());
    }

    public void setAboutText(String text) {
        aboutText.setValue(text);
    }

    public boolean isPhoneNumberMatch() {
        String originalPhoneNumber = phoneNumber.getValue();

        if (originalPhoneNumber != null) {
            String newNumber = originalPhoneNumber.replaceAll("[^\\+^\\d]", "");
            return newNumber.matches("^\\+[0-9]{11}$");
        }

        return false;
    }

    public boolean isValidPassword() {
        return Objects.requireNonNull(password.getValue()).length() >= PictureSnapApp.PASSWORD_MIN_LENGTH;
    }

    public boolean isEmailMatch() {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Objects.requireNonNull(email.getValue()).matches(emailRegex);
    }

    public boolean isValidFirstName() {
        return Objects.requireNonNull(firstName.getValue()).matches("^\\p{L}+$");
    }

    public boolean isValidLastName() {
        return Objects.requireNonNull(lastName.getValue()).matches("^\\p{L}+$");
    }

    public boolean isHaveAllData() {

        return !Objects.requireNonNull(firstName.getValue()).isEmpty() &&
                !Objects.requireNonNull(lastName.getValue()).isEmpty() &&
                !Objects.requireNonNull(email.getValue()).isEmpty() &&
                !Objects.requireNonNull(password.getValue()).isEmpty() &&
                !Objects.requireNonNull(passwordConfirmation.getValue()).isEmpty() &&
                photoSessionLocation.getValue() != null &&
                !photoSessionLocation.getValue().isInvalid() &&
                !Objects.requireNonNull(phoneNumber.getValue()).isEmpty() &&
                !Objects.requireNonNull(aboutText.getValue()).isEmpty() &&
                services.getValue() != null &&
                services.getValue().size() > 0;
    }
}
