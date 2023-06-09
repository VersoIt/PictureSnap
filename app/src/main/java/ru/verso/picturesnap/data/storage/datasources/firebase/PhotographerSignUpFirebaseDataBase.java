package ru.verso.picturesnap.data.storage.datasources.firebase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ru.verso.picturesnap.data.storage.datasources.PhotographerSignUpDataSource;
import ru.verso.picturesnap.data.storage.firebase.Constants;
import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.domain.repository.SignUpFailureCallback;

public class PhotographerSignUpFirebaseDataBase implements PhotographerSignUpDataSource {

    private final DatabaseReference databaseReference;

    private final FirebaseAuth firebaseAuth;

    private static final String ROLE = "photographer";

    private static final String ROLE_PATH = "role";

    public PhotographerSignUpFirebaseDataBase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public LiveData<Photographer> signUpPhotographer(Photographer photographer, List<PhotographerPresentationService> services, String password) {
        return signUp(photographer, services, password, null);
    }

    private LiveData<Photographer> signUp(Photographer photographer, List<PhotographerPresentationService> photographerServices, String password, SignUpFailureCallback<Photographer> signUpCallback) {
        MutableLiveData<Photographer> photographerMutableLiveData = new MutableLiveData<>(new Photographer());
        firebaseAuth.createUserWithEmailAndPassword(photographer.getEmail(), password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Executor executor = Executors.newSingleThreadExecutor();
                        executor.execute(() -> {
                            String photographerId = Objects.requireNonNull(task.getResult().getUser()).getUid();

                            createUser(photographerId);
                            createPhotographer(photographer, photographerId);
                            createServices(photographerServices, photographerId);

                            photographerMutableLiveData.postValue(photographer);
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
        return photographerMutableLiveData;
    }

    @Override
    public LiveData<Photographer> signUpPhotographer(Photographer photographer, List<PhotographerPresentationService> services, String password, SignUpFailureCallback<Photographer> signUpCallback) {
        return signUp(photographer, services, password, signUpCallback);
    }

    private void createUser(String photographerId) {
        DatabaseReference userReference = databaseReference.child(Constants.FIREBASE_USERS_PATH).child(photographerId);
        userReference.child(ROLE_PATH).setValue(ROLE);
    }

    private void createPhotographer(Photographer photographer, String photographerId) {
        DatabaseReference photographerReference = databaseReference.child(Constants.FIREBASE_PHOTOGRAPHER_PATH);
        photographer.setId(photographerId);
        photographer.setAvatarPath(Constants.FIREBASE_PHOTOGRAPHER_PATH);
        photographerReference.child(photographerId).setValue(photographer);
    }

    private void createServices(List<PhotographerPresentationService> photographerServices, String photographerId) {
        DatabaseReference serviceReference = databaseReference.child(Constants.SERVICE_PROVISIONS_PATH);
        for (PhotographerPresentationService service : photographerServices) {
            String serviceId = serviceReference.push().getKey();

            assert serviceId != null;
            service.setId(serviceId);
            service.setPhotographerId(photographerId);
            DatabaseReference currentServiceRef = serviceReference.child(serviceId);
            currentServiceRef.setValue(service);
        }
    }
}
