package ru.verso.picturesnap.data.repository;

import ru.verso.picturesnap.data.storage.datasources.UserAuthDataSource;
import ru.verso.picturesnap.domain.repository.UserAuthDataRepository;

public class UserAuthDataRepositoryImpl implements UserAuthDataRepository {

    private final UserAuthDataSource userAuthDataSource;

    public UserAuthDataRepositoryImpl(UserAuthDataSource userAuthDataSource) {
        this.userAuthDataSource = userAuthDataSource;
    }

    @Override
    public String getId() {
        return userAuthDataSource.getId();
    }
}