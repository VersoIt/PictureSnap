package ru.verso.picturesnap.data.repository;

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
}
