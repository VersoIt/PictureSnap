package ru.verso.picturesnap.domain.repository;

import java.util.List;
import java.util.function.Consumer;

import ru.verso.picturesnap.domain.models.Record;

public interface RecordsRepository {

    void insertNewRecord(Record record);

    void getRecordsOfPhotographer(String photographerId, Consumer<List<Record>> recordsCallback);

    void getRecordsOfClient(String clientId, Consumer<List<Record>> recordsCallback);

    void updateRecordStatus(String recordId, Record.Status status);
}
