package ru.verso.picturesnap.data.repository;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import ru.verso.picturesnap.domain.repository.PasswordRecoverRepository;
import ru.verso.picturesnap.domain.repository.PasswordResetCallback;

public class PasswordRecoverRepositoryImpl implements PasswordRecoverRepository {

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
