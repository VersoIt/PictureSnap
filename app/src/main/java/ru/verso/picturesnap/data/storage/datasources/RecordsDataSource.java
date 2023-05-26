package ru.verso.picturesnap.data.storage.datasources;

import ru.verso.picturesnap.domain.models.Record;

public interface RecordsDataSource {

    void insertNewRecord(Record record);
}
