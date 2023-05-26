package ru.verso.picturesnap.data.repository;

import ru.verso.picturesnap.data.storage.datasources.PasswordRecoverDataSource;
import ru.verso.picturesnap.domain.repository.PasswordRecoverRepository;
import ru.verso.picturesnap.domain.repository.PasswordResetCallback;

public class PasswordRecoverRepositoryImpl implements PasswordRecoverRepository {

    private final PasswordRecoverDataSource passwordRecoverDataSource;

    public PasswordRecoverRepositoryImpl(PasswordRecoverDataSource passwordRecoverDataSource) {
        this.passwordRecoverDataSource = passwordRecoverDataSource;
    }

    @Override
    public void sendPasswordResetTo(String email, PasswordResetCallback callback) {
        passwordRecoverDataSource.sendPasswordResetTo(email, callback);
    }
}
