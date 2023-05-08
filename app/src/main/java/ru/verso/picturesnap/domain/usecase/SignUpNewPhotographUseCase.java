package ru.verso.picturesnap.domain.usecase;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.models.PhotographPresentationService;
import ru.verso.picturesnap.domain.repository.PhotographRegistrationRepository;

public class SignUpNewPhotographUseCase {

    private final PhotographRegistrationRepository photographRepository;

    public SignUpNewPhotographUseCase(PhotographRegistrationRepository photographRepository) {
        this.photographRepository = photographRepository;
    }

    public void signUpPhotograph(Photograph photograph, List<PhotographPresentationService> services, String password) {
        photographRepository.signUpPhotograph(photograph, services, password);
    }
}
