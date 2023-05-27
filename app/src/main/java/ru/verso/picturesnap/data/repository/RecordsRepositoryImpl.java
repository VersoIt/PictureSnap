package ru.verso.picturesnap.data.repository;

import java.util.List;
import java.util.function.Consumer;

import ru.verso.picturesnap.data.storage.datasources.RecordsDataSource;
import ru.verso.picturesnap.domain.models.Record;
import ru.verso.picturesnap.domain.repository.RecordsRepository;

public class RecordsRepositoryImpl implements RecordsRepository {

    private final RecordsDataSource recordsDataSource;

    public RecordsRepositoryImpl(RecordsDataSource recordsDataSource) {
        this.recordsDataSource = recordsDataSource;
    }

    @Override
    public void insertNewRecord(Record record) {
        recordsDataSource.insertNewRecord(record);
    }

    @Override
    public void getRecordsOfPhotographer(String photographerId, Consumer<List<Record>> recordsCallback) {
        recordsDataSource.getRecordsOfPhotographer(photographerId, recordsCallback);
    }

    @Override
    public void getRecordsOfClient(String clientId, Consumer<List<Record>> recordsCallback) {
        recordsDataSource.getRecordsOfClient(clientId, recordsCallback);
    }

    @Override
    public void updateRecordStatus(String recordId, Record.Status status) {
        recordsDataSource.updateRecordStatus(recordId, status);
    }
}
