package ru.verso.picturesnap.data.storage.datasources;

import ru.verso.picturesnap.domain.repository.RoleRepository;

public interface RoleDataSource {

    RoleRepository.Role getRole();

    void updateRole(RoleRepository.Role role);
}
