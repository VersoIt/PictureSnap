package ru.verso.picturesnap.domain.repository;

import ru.verso.picturesnap.domain.models.Record;

public interface RecordsRepository {

    void insertNewRecord(Record record);
}
