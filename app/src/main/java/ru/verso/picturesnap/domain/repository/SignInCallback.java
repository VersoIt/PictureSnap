package ru.verso.picturesnap.domain.repository;

public interface SignInCallback<T> {

    void onNotFoundUser();

    void onWrongPassword();

    void onNetworkError();
}