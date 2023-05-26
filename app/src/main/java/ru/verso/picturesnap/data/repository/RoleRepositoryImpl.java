package ru.verso.picturesnap.data.repository;

import ru.verso.picturesnap.data.storage.datasources.RoleDataSource;
import ru.verso.picturesnap.domain.repository.RoleRepository;

public class RoleRepositoryImpl implements RoleRepository {

    private final RoleDataSource roleDataSource;

    public RoleRepositoryImpl(RoleDataSource roleDataSource) {
        this.roleDataSource = roleDataSource;
    }

    @Override
    public Role getRole() {
        return roleDataSource.getRole();
    }

    @Override
    public void updateRole(Role role) {
        roleDataSource.updateRole(role);
    }
}
