package ru.verso.picturesnap.domain.usecase;

import java.util.Date;

import ru.verso.picturesnap.domain.models.Record;
import ru.verso.picturesnap.domain.repository.RecordsRepository;

public class BookPhotographerUseCase {

    private final RecordsRepository recordsRepository;

    public BookPhotographerUseCase(RecordsRepository recordsRepository) {
        this.recordsRepository = recordsRepository;
    }

    public void book(String serviceId, String clientId, String photographerId, Date date, String comment) {
        Record record = new Record(serviceId, clientId, photographerId, date, Record.Status.PENDING, comment);
        recordsRepository.insertNewRecord(record);
    }
}
