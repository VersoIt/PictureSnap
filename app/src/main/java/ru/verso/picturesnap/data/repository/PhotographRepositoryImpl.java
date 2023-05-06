package ru.verso.picturesnap.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.models.PhotographPresentationService;
import ru.verso.picturesnap.domain.models.PhotographService;
import ru.verso.picturesnap.domain.repository.PhotographRepository;

public class PhotographRepositoryImpl implements PhotographRepository {

    private static final String SERVICES_REF = "services";
    private static final String PHOTOGRAPHS_REF = "photographers";
    private static final String SERVICE_PROVISIONS_REF = "service_provisions";

    private final DatabaseReference servicesReference;
    private final DatabaseReference photographsReference;
    private final DatabaseReference serviceProvisionReference;

    public PhotographRepositoryImpl() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        servicesReference = database.getReference(SERVICES_REF);
        photographsReference = database.getReference(PHOTOGRAPHS_REF);
        serviceProvisionReference = database.getReference(SERVICE_PROVISIONS_REF);
    }

    @Override
    public LiveData<List<Photograph>> getAllPhotographs() {
        MutableLiveData<List<Photograph>> photographsLiveData = new MutableLiveData<>(new ArrayList<>());

        photographsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Photograph> photographs = new ArrayList<>();
                for (DataSnapshot photographSnapshot : snapshot.getChildren()) {
                    photographs.add(photographSnapshot.getValue(Photograph.class));
                }
                photographsLiveData.setValue(photographs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return photographsLiveData;
    }

    @Override
    public LiveData<List<PhotographService>> getAllPhotographServices() {

        MutableLiveData<List<PhotographService>> photographServices = new MutableLiveData<>(new ArrayList<>());

        servicesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<PhotographService> services = new ArrayList<>();
                for (DataSnapshot serviceSnapshot : snapshot.getChildren()) {
                    PhotographService service = serviceSnapshot.getValue(PhotographService.class);
                    services.add(service);
                }
                photographServices.setValue(services);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return photographServices;
    }

    @Override
    public LiveData<Photograph> getPhotographById(String id) {
        MutableLiveData<Photograph> photographMutableLiveData = new MutableLiveData<>();

        Query query = photographsReference.orderByChild("id").equalTo(id);
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    photographMutableLiveData.setValue(dataSnapshot.getValue(Photograph.class));
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return photographMutableLiveData;
    }

    @Override
    public LiveData<List<PhotographPresentationService>> getPhotographServicesById(String photographId) {
        MutableLiveData<List<PhotographPresentationService>> photographPresentationServiceMutableLiveData = new MutableLiveData<>();

        Query query = serviceProvisionReference.orderByChild("photographId").equalTo(photographId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<PhotographPresentationService> services = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    services.add(dataSnapshot.getValue(PhotographPresentationService.class));
                }
                photographPresentationServiceMutableLiveData.setValue(services);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return photographPresentationServiceMutableLiveData;
    }
}