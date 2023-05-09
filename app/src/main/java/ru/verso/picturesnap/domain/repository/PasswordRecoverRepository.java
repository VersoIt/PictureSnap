package ru.verso.picturesnap.domain.repository;

public interface PasswordRecoverRepository {

    void sendPasswordResetTo(String email, PasswordResetCallback callback);
}
