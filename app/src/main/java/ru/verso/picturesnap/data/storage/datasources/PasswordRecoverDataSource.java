package ru.verso.picturesnap.data.storage.datasources;

import ru.verso.picturesnap.domain.repository.PasswordResetCallback;

public interface PasswordRecoverDataSource {

    void sendPasswordResetTo(String email, PasswordResetCallback callback);
}
