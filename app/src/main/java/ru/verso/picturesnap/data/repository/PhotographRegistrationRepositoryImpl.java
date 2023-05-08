package ru.verso.picturesnap.data.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Objects;

import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.models.PhotographPresentationService;
import ru.verso.picturesnap.domain.repository.PhotographRegistrationRepository;
import ru.verso.picturesnap.presentation.app.PictureSnapApp;

public class PhotographRegistrationRepositoryImpl implements PhotographRegistrationRepository {

    private final DatabaseReference databaseReference;

    private final FirebaseAuth firebaseAuth;

    private static final String ROLE = "photographer";

    private static final String ROLE_PATH = "role";

    public PhotographRegistrationRepositoryImpl() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void signUpPhotograph(Photograph photograph, List<PhotographPresentationService> photographServices, String password) {
        firebaseAuth.createUserWithEmailAndPassword(photograph.getEmail(), password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        String photographId = Objects.requireNonNull(task.getResult().getUser()).getUid();

                        createUser(photographId);
                        createPhotograph(photograph, photographId);
                        createServices(photographServices, photographId);
                    }
                });
    }

    private void createUser(String photographId) {
        DatabaseReference userReference = databaseReference.child(PictureSnapApp.FIREBASE_USERS_PATH).child(photographId);
        userReference.child(ROLE_PATH).setValue(ROLE);
    }

    private void createPhotograph(Photograph photograph, String photographId) {
        DatabaseReference photographReference = databaseReference.child(PictureSnapApp.FIREBASE_PHOTOGRAPH_PATH);
        photograph.setId(photographId);
        photographReference.child(photographId).setValue(photograph);
    }

    private void createServices(List<PhotographPresentationService> photographServices, String photographId) {
        DatabaseReference serviceReference = databaseReference.child(PictureSnapApp.SERVICE_PROVISIONS_PATH);
        for (PhotographPresentationService service : photographServices) {
            String serviceId = serviceReference.push().getKey();

            assert serviceId != null;
            service.setId(serviceId);
            service.setPhotographId(photographId);
            DatabaseReference currentServiceRef = serviceReference.child(serviceId);
            currentServiceRef.setValue(service);
        }
    }
}
