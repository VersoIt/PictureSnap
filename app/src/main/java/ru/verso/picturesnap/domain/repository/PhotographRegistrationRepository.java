package ru.verso.picturesnap.domain.repository;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.models.PhotographPresentationService;
import ru.verso.picturesnap.domain.models.PhotographService;

public interface PhotographRegistrationRepository {

    void signUpPhotograph(Photograph photograph, List<PhotographPresentationService> services, String password);
}
