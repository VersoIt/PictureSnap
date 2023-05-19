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

import ru.verso.picturesnap.data.storage.firebase.Constants;
import ru.verso.picturesnap.domain.models.PhotographerService;
import ru.verso.picturesnap.domain.repository.PhotographerServiceRepository;
import ru.verso.picturesnap.presentation.app.PictureSnapApp;

public class PhotographerServiceRepositoryImpl implements PhotographerServiceRepository {

    private final DatabaseReference databaseReference;

    public PhotographerServiceRepositoryImpl() {
        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.SERVICE_PATH);
    }

    @Override
    public LiveData<List<PhotographerService>> getAllServices() {

        MutableLiveData<List<PhotographerService>> mutableLiveData = new MutableLiveData<>(new ArrayList<>());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<PhotographerService> services = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                    services.add(dataSnapshot.getValue(PhotographerService.class));

                mutableLiveData.setValue(services);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return mutableLiveData;
    }

    @Override
    public LiveData<PhotographerService> getServiceById(String id) {
        MutableLiveData<PhotographerService> mutableLiveData = new MutableLiveData<>(new PhotographerService());

        Query serviceQuery = databaseReference.orderByChild("id").equalTo(id);
        serviceQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                    mutableLiveData.setValue(dataSnapshot.getValue(PhotographerService.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return mutableLiveData;
    }
}
