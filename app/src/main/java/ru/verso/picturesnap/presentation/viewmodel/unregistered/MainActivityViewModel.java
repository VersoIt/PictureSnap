package ru.verso.picturesnap.presentation.viewmodel.unregistered;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.presentation.activity.ClientActivity;
import ru.verso.picturesnap.presentation.activity.PhotographerActivity;

public class MainActivityViewModel extends ViewModel {

    private final GetUserDataUseCase getUserDataUseCase;

    public MainActivityViewModel(GetUserDataUseCase getUserDataUseCase) {
        this.getUserDataUseCase = getUserDataUseCase;
    }

    public Class<? extends AppCompatActivity> getClassToNavigate() {
        RoleRepository.Role role = getRole();

        switch (role) {
            case CLIENT:
            case UNREGISTERED:
                return ClientActivity.class;
            case PHOTOGRAPH:
                return PhotographerActivity.class;
        }

        return ClientActivity.class;
    }

    public RoleRepository.Role getRole() {
        return getUserDataUseCase.getRole();
    }
}
