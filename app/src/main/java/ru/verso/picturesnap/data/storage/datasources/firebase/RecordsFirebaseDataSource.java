package ru.verso.picturesnap.data.storage.datasources.firebase;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import ru.verso.picturesnap.data.storage.datasources.RecordsDataSource;
import ru.verso.picturesnap.data.storage.firebase.Constants;
import ru.verso.picturesnap.domain.models.Record;

public class RecordsFirebaseDataSource implements RecordsDataSource {

    private final DatabaseReference recordsRef;

    public RecordsFirebaseDataSource() {
        recordsRef = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_RECORDS_PATH);
    }

    @Override
    public void insertNewRecord(Record record) {
        String key = recordsRef.push().getKey();
        record.setId(key);

        if (key != null)
            recordsRef.child(key).setValue(record);
    }

    @Override
    public void getRecordsOfPhotographer(String photographerId, Consumer<List<Record>> recordsCallback) {
        recordsRef.orderByChild(Constants.FIREBASE_PHOTOGRAPHER_ID_CHILD_PATH).equalTo(photographerId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<Record> records = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren())
                        records.add(dataSnapshot.getValue(Record.class));

                    recordsCallback.accept(records);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void getRecordsOfClient(String clientId, Consumer<List<Record>> recordsCallback) {
        recordsRef.orderByChild(Constants.FIREBASE_CLIENT_ID_CHILD_PATH).equalTo(clientId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<Record> records = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren())
                        records.add(dataSnapshot.getValue(Record.class));

                    recordsCallback.accept(records);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void updateRecordStatus(String recordId, Record.Status status) {
        recordsRef.child(recordId).child(Constants.FIREBASE_STATUS_CHILD_PATH).setValue(status);
    }
}
