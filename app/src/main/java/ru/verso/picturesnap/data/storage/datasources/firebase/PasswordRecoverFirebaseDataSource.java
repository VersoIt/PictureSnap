package ru.verso.picturesnap.data.storage.datasources.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import ru.verso.picturesnap.data.storage.datasources.PasswordRecoverDataSource;
import ru.verso.picturesnap.domain.repository.PasswordResetCallback;

public class PasswordRecoverFirebaseDataSource implements PasswordRecoverDataSource {

    @Override
    public void sendPasswordResetTo(String email, PasswordResetCallback callback) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onSuccess();
            }
            else {
                if (task.getException() instanceof FirebaseAuthInvalidUserException)
                    callback.onNotFoundEmail();
                else
                    callback.onNetworkError();
            }
        });
    }
}
