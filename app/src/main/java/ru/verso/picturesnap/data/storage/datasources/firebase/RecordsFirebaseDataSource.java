package ru.verso.picturesnap.data.storage.datasources.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        if (key != null)
            recordsRef.child(key).setValue(record);
    }
}
