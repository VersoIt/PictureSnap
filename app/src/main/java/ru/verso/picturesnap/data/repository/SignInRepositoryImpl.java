package ru.verso.picturesnap.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import ru.verso.picturesnap.data.storage.firebase.Constants;
import ru.verso.picturesnap.domain.models.User;
import ru.verso.picturesnap.domain.repository.SignInCallback;
import ru.verso.picturesnap.domain.repository.SignInRepository;

public class SignInRepositoryImpl implements SignInRepository {

    private final FirebaseAuth firebaseAuth;

    private final DatabaseReference userRef;

    public SignInRepositoryImpl() {
        this.firebaseAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_USERS_PATH);
    }

    @Override
    public LiveData<User> signInUser(String email, String password, SignInCallback<User> callback) {
        return signIn(email, password, callback);
    }

    private LiveData<User> signIn(String email, String password, SignInCallback<User> callback) {

        MutableLiveData<User> userMutableLiveData = new MutableLiveData<>(new User());
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        String uId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

                        userRef.child(uId).addListenerForSingleValueEvent(new ValueEventListener() {
                            User user = new User();

                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                user = snapshot.getValue(User.class);
                                if (user != null) {
                                    user.setId(uId);
                                    userMutableLiveData.setValue(user);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    else {
                        if (callback != null) {
                            if (task.getException() instanceof FirebaseAuthInvalidUserException)
                                callback.onNotFoundUser();
                            else if(task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                                callback.onWrongPassword();
                            else
                                callback.onNetworkError();
                        }
                    }
                });

        return userMutableLiveData;
    }

    @Override
    public LiveData<User> signInUser(String email, String password) {
        return signIn(email, password, null);
    }
}
