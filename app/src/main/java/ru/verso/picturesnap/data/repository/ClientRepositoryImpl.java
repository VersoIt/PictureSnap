package ru.verso.picturesnap.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ru.verso.picturesnap.domain.models.Client;
import ru.verso.picturesnap.domain.repository.ClientRepository;
import ru.verso.picturesnap.presentation.app.PictureSnapApp;

public class ClientRepositoryImpl implements ClientRepository {

    private final DatabaseReference databaseReference;

    public ClientRepositoryImpl() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference(PictureSnapApp.FIREBASE_CLIENT_PATH);
    }

    @Override
    public LiveData<Client> getClientById(String id) {
        DatabaseReference clientReference = databaseReference.child(id);
        MutableLiveData<Client> clientMutableLiveData = new MutableLiveData<>();

        clientReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clientMutableLiveData.setValue(snapshot.getValue(Client.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return clientMutableLiveData;
    }
}
