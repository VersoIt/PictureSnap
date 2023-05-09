package ru.verso.picturesnap.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ru.verso.picturesnap.domain.models.PhotographerService;
import ru.verso.picturesnap.domain.repository.ServicesRepository;

public class ServicesRepositoryImpl implements ServicesRepository {

    private static final String SERVICES_REF = "services";

    private final DatabaseReference servicesReference;

    public ServicesRepositoryImpl() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        servicesReference = database.getReference(SERVICES_REF);
    }

    @Override
    public LiveData<List<PhotographerService>> getAllServices() {

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
                Log.e("NONENONE", "FUCKER");
            }
        });

        return photographerServices;
    }
}
