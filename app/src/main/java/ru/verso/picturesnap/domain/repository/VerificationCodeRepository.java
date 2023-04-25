package ru.verso.picturesnap.domain.repository;

public interface VerificationCodeRepository {

    String getVerificationKey();

    void updateVerificationKey(String key);
}
