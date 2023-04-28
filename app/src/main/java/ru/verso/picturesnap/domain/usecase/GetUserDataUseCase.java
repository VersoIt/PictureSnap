package ru.verso.picturesnap.domain.usecase;

import ru.verso.picturesnap.domain.repository.FirstTimeWentRepository;
import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.domain.repository.UserLocationRepository;

public class GetUserDataUseCase {

    private final UserLocationRepository userLocationRepository;

    private final FirstTimeWentRepository firstTimeWentRepository;

    private final RoleRepository roleRepository;

    public GetUserDataUseCase(UserLocationRepository userLocationRepository,
                              RoleRepository roleRepository,
                              FirstTimeWentRepository firstTimeWentRepository) {

        this.userLocationRepository = userLocationRepository;
        this.firstTimeWentRepository = firstTimeWentRepository;
        this.roleRepository = roleRepository;
    }

    public RoleRepository.Role getCurrentRole() {
        return roleRepository.getRole();
    }

    public boolean isFirstCome() {
        return firstTimeWentRepository.isFirst();
    }

    public String getLocation() {
        return userLocationRepository.getLocation();
    }

    public RoleRepository.Role getRole() {
        return roleRepository.getRole();
    }
}
