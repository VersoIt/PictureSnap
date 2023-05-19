package ru.verso.picturesnap.data.repository;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ru.verso.picturesnap.data.storage.firebase.Constants;
import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.domain.models.PhotographerService;
import ru.verso.picturesnap.domain.repository.PhotographerRepository;

public class PhotographerRepositoryImpl implements PhotographerRepository {

    private static final String SERVICES_REF = "services";
    private static final String PHOTOGRAPHS_REF = "photographers";
    private static final String SERVICE_PROVISIONS_REF = "service_provisions";
    private static final String JPG_EXTENSION = ".jpg";

    private final DatabaseReference servicesReference;
    private final DatabaseReference photographersReference;
    private final DatabaseReference serviceProvisionReference;

    public PhotographerRepositoryImpl() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        servicesReference = database.getReference(SERVICES_REF);
        photographersReference = database.getReference(PHOTOGRAPHS_REF);
        serviceProvisionReference = database.getReference(SERVICE_PROVISIONS_REF);
    }

    @Override
    public LiveData<List<Photographer>> getAllPhotographers() {
        MutableLiveData<List<Photographer>> photographersLiveData = new MutableLiveData<>(new ArrayList<>());

        photographersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Photographer> photographers = new ArrayList<>();
                for (DataSnapshot photographerSnapshot : snapshot.getChildren()) {
                    photographers.add(photographerSnapshot.getValue(Photographer.class));
                }
                photographersLiveData.setValue(photographers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return photographersLiveData;
    }

    @Override
    public LiveData<List<PhotographerService>> getAllPhotographerServices() {

        MutableLiveData<List<PhotographerService>> photographerServices = new MutableLiveData<>(new ArrayList<>());

        servicesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<PhotographerService> services = new ArrayList<>();
                for (DataSnapshot serviceSnapshot : snapshot.getChildren()) {
                    PhotographerService service = serviceSnapshot.getValue(PhotographerService.class);
                    services.add(service);
                }
                photographerServices.setValue(services);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return photographerServices;
    }

    @Override
    public LiveData<Photographer> getPhotographerById(String id) {
        MutableLiveData<Photographer> photographerMutableLiveData = new MutableLiveData<>();

        Query query = photographersReference.orderByChild(Constants.FIREBASE_USER_ID_CHILD).equalTo(id);
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    photographerMutableLiveData.setValue(dataSnapshot.getValue(Photographer.class));
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return photographerMutableLiveData;
    }

    @Override
    public LiveData<List<PhotographerPresentationService>> getPhotographerServicesById(String photographerId) {
        MutableLiveData<List<PhotographerPresentationService>> photographPresentationServiceMutableLiveData = new MutableLiveData<>();

        Query query = serviceProvisionReference.orderByChild(Constants.FIREBASE_PHOTOGRAPHER_ID_CHILD_IN_SERVICES).equalTo(photographerId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<PhotographerPresentationService> services = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    services.add(dataSnapshot.getValue(PhotographerPresentationService.class));
                }
                photographPresentationServiceMutableLiveData.setValue(services);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return photographPresentationServiceMutableLiveData;
    }

    private Query getPhotographerQueryById(String photographerId) {
        final String photographerIdPath = Constants.FIREBASE_PHOTOGRAPHER_ID_CHILD_IN_SERVICES;
        return photographersReference.orderByChild(photographerIdPath).equalTo(photographerId);
    }

    private void updatePhotographerAvatarPath(String photographerId, String url) {
        Query query = getPhotographerQueryById(photographerId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DatabaseReference reference = snapshot.child(photographerId).child(Constants.FIREBASE_PHOTOGRAPHER_AVATAR_CHILD).getRef();
                reference.setValue(url);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void updatePhotographerAvatar(String photographerId, Uri path) {
        String imageName = UUID.randomUUID().toString() + JPG_EXTENSION;
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(Constants.FIREBASE_AVATAR_PHOTOGRAPHER_PATH + imageName);
        UploadTask uploadTask = storageReference.putFile(path);

        uploadTask.addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
            String imageUri = uri.toString();
            updatePhotographerAvatarPath(photographerId, imageUri);
        }));
    }
}