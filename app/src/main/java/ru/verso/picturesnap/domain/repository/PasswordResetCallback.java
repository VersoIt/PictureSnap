package ru.verso.picturesnap.domain.repository;

public interface PasswordResetCallback {

    void onSuccess();

    void onNotFoundEmail();

    void onNetworkError();
}
