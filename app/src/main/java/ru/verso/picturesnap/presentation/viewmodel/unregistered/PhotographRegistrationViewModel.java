package ru.verso.picturesnap.presentation.viewmodel.unregistered;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import ru.verso.picturesnap.domain.models.Location;
import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.models.PhotographPresentationService;
import ru.verso.picturesnap.domain.usecase.SignUpNewPhotographUseCase;

public class PhotographRegistrationViewModel extends ViewModel {

    private final MutableLiveData<String> firstName;

    private final MutableLiveData<String> lastName;

    private final MutableLiveData<String> email;

    private final MutableLiveData<String> password;

    private final MutableLiveData<Location> photoSessionLocation;

    private final MutableLiveData<String> phoneNumber;

    private final MutableLiveData<Integer> experience;

    private final MutableLiveData<String> aboutText;

    private final MutableLiveData<String> passwordConfirmation;

    private final MutableLiveData<List<PhotographPresentationService>> services;

    private final SignUpNewPhotographUseCase signUpNewPhotographUseCase;

    public PhotographRegistrationViewModel(SignUpNewPhotographUseCase signUpNewPhotographUseCase) {

        this.signUpNewPhotographUseCase = signUpNewPhotographUseCase;

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
            value = Character.toUpperCase(value.charAt(0)) + value.substring(1);
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

    public LiveData<List<PhotographPresentationService>> getServices() {
        return services;
    }

    public void setServices(List<PhotographPresentationService> services) {
        this.services.setValue(services.stream().filter(s -> s.getCost() > 0).collect(Collectors.toList()));
    }



    public void save() {

        Photograph.Builder photographBuilder = new Photograph.Builder();
        photographBuilder.setName(getFirstName().getValue(), getLastName().getValue());
        photographBuilder.setEmail(getEmail().getValue());
        photographBuilder.setLocation(Objects.requireNonNull(photoSessionLocation.getValue()).getLatitude(), photoSessionLocation.getValue().getLongitude());
        photographBuilder.setPhoneNumber(phoneNumber.getValue());
        photographBuilder.setExperience(experience.getValue());
        photographBuilder.setDescription(aboutText.getValue());
        photographBuilder.setAvatarPath("");

        signUpNewPhotographUseCase.signUpPhotograph(photographBuilder.create(), services.getValue(), password.getValue());
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
        return Objects.requireNonNull(password.getValue()).length() >= 8;
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
