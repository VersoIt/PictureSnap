package ru.verso.picturesnap.domain.repository;

public interface RoleRepository {

    Role getRole();

    void updateRole(Role role);

    enum Role {
        UNREGISTERED(0),
        CLIENT(1),
        PHOTOGRAPH(2);

        private final int value;

        Role(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    int DEFAULT = 0;
}