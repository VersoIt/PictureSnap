package ru.verso.picturesnap.data.storage.datasources.firebase;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import ru.verso.picturesnap.data.storage.datasources.ClientDataSource;
import ru.verso.picturesnap.data.storage.firebase.Constants;
import ru.verso.picturesnap.domain.models.Client;


public class ClientFirebaseDataSource implements ClientDataSource {

    private final DatabaseReference databaseReference;

    public ClientFirebaseDataSource() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CLIENT_PATH);
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

    private void updateClientAvatarPath(String clientId, String uri) {
        Map<String, Object> clientDataToUpdate = new HashMap<>();
        clientDataToUpdate.put(Constants.FIREBASE_CLIENT_AVATAR_CHILD, uri);

        databaseReference.child(clientId).updateChildren(clientDataToUpdate);
    }

    @Override
    public void updateAvatar(String clientId, Uri uri) {
        String imageName = UUID.randomUUID().toString() + ".jpg";
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(Constants.FIREBASE_AVATAR_CLIENT_PATH + imageName);
        UploadTask uploadTask = storageReference.putFile(uri);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            String imageUri = uri.toString();
            updateClientAvatarPath(clientId, imageUri);
        });
    }
}