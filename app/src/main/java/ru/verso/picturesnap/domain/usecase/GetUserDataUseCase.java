package ru.verso.picturesnap.domain.usecase;

import com.google.firebase.auth.FirebaseAuth;

import ru.verso.picturesnap.domain.models.Location;
import ru.verso.picturesnap.domain.repository.FirstTimeWentRepository;
import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.domain.repository.UserAuthDataRepository;
import ru.verso.picturesnap.domain.repository.UserLocationRepository;

public class GetUserDataUseCase {

    private final UserLocationRepository userLocationRepository;

    private final FirstTimeWentRepository firstTimeWentRepository;

    private final RoleRepository roleRepository;

    private final UserAuthDataRepository userAuthDataRepository;

    public GetUserDataUseCase(UserLocationRepository userLocationRepository,
                              RoleRepository roleRepository,
                              FirstTimeWentRepository firstTimeWentRepository,
                              UserAuthDataRepository userAuthDataRepository) {

        this.userLocationRepository = userLocationRepository;
        this.firstTimeWentRepository = firstTimeWentRepository;
        this.roleRepository = roleRepository;
        this.userAuthDataRepository = userAuthDataRepository;
    }

    public void signOut() {
        roleRepository.updateRole(RoleRepository.Role.UNREGISTERED);
        FirebaseAuth.getInstance().signOut();
    }

    public String getUserId() {
        return userAuthDataRepository.getId();
    }

    public boolean isFirstCome() {
        return firstTimeWentRepository.isFirst();
    }

    public Location getLocation() {
        return userLocationRepository.getLocation();
    }

    public RoleRepository.Role getRole() {
        return roleRepository.getRole();
    }
}
