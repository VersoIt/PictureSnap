package ru.verso.picturesnap.domain.usecase;

import ru.verso.picturesnap.domain.models.Location;
import ru.verso.picturesnap.domain.repository.FirstTimeWentRepository;
import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.domain.repository.UserLocationRepository;

public class UpdateUserDataUseCase {

    private final UserLocationRepository userLocationRepository;

    private final FirstTimeWentRepository firstTimeWentRepository;

    private final RoleRepository roleRepository;

    public UpdateUserDataUseCase(RoleRepository roleRepository,
                                 UserLocationRepository locationRepository,
                                 FirstTimeWentRepository firstTimeWentRepository) {

        this.userLocationRepository = locationRepository;
        this.firstTimeWentRepository = firstTimeWentRepository;
        this.roleRepository = roleRepository;
    }

    public Location getLocation() {
        return userLocationRepository.getLocation();
    }

    public void setLocation(double latitude, double longitude) {
        userLocationRepository.setLocation(latitude, longitude);
    }

    public void setRole(RoleRepository.Role role) {
        roleRepository.updateRole(role);
    }

    public void setVisited() {
        firstTimeWentRepository.setVisited();
    }

}
