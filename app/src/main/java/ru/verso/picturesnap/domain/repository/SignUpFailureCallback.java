package ru.verso.picturesnap.domain.repository;

public interface SignUpFailureCallback<T> {

    void onUserCollision();

    void onNetworkError();
}
