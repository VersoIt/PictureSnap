package ru.verso.picturesnap.data.storage.datasources.firebase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ru.verso.picturesnap.data.storage.datasources.ClientSignUpDataSource;
import ru.verso.picturesnap.data.storage.firebase.Constants;
import ru.verso.picturesnap.domain.models.Client;
import ru.verso.picturesnap.domain.repository.SignUpFailureCallback;

public class ClientSignUpFirebaseDataSource implements ClientSignUpDataSource {


    private final DatabaseReference databaseReference;

    private final FirebaseAuth firebaseAuth;

    private static final String ROLE = "client";

    private static final String ROLE_PATH = "role";

    public ClientSignUpFirebaseDataSource() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public LiveData<Client> signUpClient(Client client, String password) {
        return signUp(client, password, null);
    }

    private LiveData<Client> signUp(Client client, String password, SignUpFailureCallback<Client> signUpCallback) {

        MutableLiveData<Client> clientMutableLiveData = new MutableLiveData<>(new Client());
        firebaseAuth.createUserWithEmailAndPassword(client.getEmail(), password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Executor executor = Executors.newSingleThreadExecutor();
                        executor.execute(() -> {
                            String clientId = Objects.requireNonNull(task.getResult().getUser()).getUid();

                            createUser(clientId);
                            createClient(client, clientId);

                            clientMutableLiveData.postValue(client);
                        });
                    } else {
                        if (signUpCallback != null) {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                signUpCallback.onUserCollision();
                            else
                                signUpCallback.onNetworkError();
                        }
                    }
                });

        return clientMutableLiveData;
    }

    @Override
    public LiveData<Client> signUpClient(Client client, String password, SignUpFailureCallback<Client> signUpCallback) {
        return signUp(client, password, signUpCallback);
    }

    private void createUser(String clientId) {
        DatabaseReference userReference = databaseReference.child(Constants.FIREBASE_USERS_PATH).child(clientId);
        userReference.child(ROLE_PATH).setValue(ROLE);
    }

    private void createClient(Client client, String clientId) {
        DatabaseReference clientReference = databaseReference.child(Constants.FIREBASE_CLIENT_PATH);
        client.setId(clientId);
        clientReference.child(clientId).setValue(client);
    }
}
