package ru.verso.picturesnap.domain.usecase;

import ru.verso.picturesnap.domain.repository.FirstTimeWentRepository;
import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.domain.repository.UserLocationRepository;

public class OperationUserDataUseCase {

    private final RoleRepository roleRepository;

    private final UserLocationRepository userLocationRepository;

    private final FirstTimeWentRepository firstTimeWentRepository;

    public OperationUserDataUseCase(RoleRepository roleRepository,
                                    UserLocationRepository locationRepository,
                                    FirstTimeWentRepository firstTimeWentRepository) {

        this.roleRepository = roleRepository;
        this.userLocationRepository = locationRepository;
        this.firstTimeWentRepository = firstTimeWentRepository;
    }

    public String getLocation() {
        return userLocationRepository.getLocation();
    }

    public void setLocation(String location) {
        if (location.isEmpty()) {
            userLocationRepository.setLocation(userLocationRepository.DEFAULT_VALUE);
            return;
        }
        userLocationRepository.setLocation(location);
    }

    public RoleRepository.Role getCurrentRole() {
        return roleRepository.getRole();
    }

    public boolean isFirstCome() {
        return firstTimeWentRepository.isFirst();
    }

    public void setVisited() {
        firstTimeWentRepository.setVisited();
    }

}
