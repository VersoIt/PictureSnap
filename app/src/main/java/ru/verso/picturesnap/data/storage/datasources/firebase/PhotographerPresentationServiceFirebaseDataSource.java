package ru.verso.picturesnap.data.storage.datasources.firebase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ru.verso.picturesnap.data.storage.PhotographerPresentationServiceDataSource;
import ru.verso.picturesnap.data.storage.firebase.Constants;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;

public class PhotographerPresentationServiceFirebaseDataSource implements PhotographerPresentationServiceDataSource {

    private final FirebaseDatabase firebaseDatabase;

    public PhotographerPresentationServiceFirebaseDataSource() {
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public LiveData<List<PhotographerPresentationService>> getAllPresentationServices() {
        MutableLiveData<List<PhotographerPresentationService>> photographerPresentationServiceLiveData = new MutableLiveData<>(new ArrayList<>());
        firebaseDatabase.getReference(Constants.SERVICE_PROVISIONS_PATH).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<PhotographerPresentationService> services = new ArrayList<>();
                for (DataSnapshot currentSnapshot : snapshot.getChildren())
                    services.add(currentSnapshot.getValue(PhotographerPresentationService.class));

                photographerPresentationServiceLiveData.setValue(services);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return photographerPresentationServiceLiveData;
    }

    @Override
    public LiveData<List<PhotographerPresentationService>> getPresentationServicesByServiceId(String id) {
        MutableLiveData<List<PhotographerPresentationService>> photographerPresentationServiceLiveData = new MutableLiveData<>(new ArrayList<>());
        firebaseDatabase.getReference(Constants.SERVICE_PROVISIONS_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<PhotographerPresentationService> services = new ArrayList<>();
                for (DataSnapshot currentSnapshot : snapshot.getChildren()) {
                    PhotographerPresentationService currentService = currentSnapshot.getValue(PhotographerPresentationService.class);

                    if (currentService != null && currentService.getServiceId().equals(id))
                        services.add(currentService);
                }

                photographerPresentationServiceLiveData.setValue(services);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return photographerPresentationServiceLiveData;
    }
}
