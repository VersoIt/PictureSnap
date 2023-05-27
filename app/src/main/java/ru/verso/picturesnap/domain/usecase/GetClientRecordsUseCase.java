package ru.verso.picturesnap.domain.usecase;

import java.util.List;
import java.util.function.Consumer;

import ru.verso.picturesnap.domain.models.Record;
import ru.verso.picturesnap.domain.repository.RecordsRepository;

public class GetClientRecordsUseCase {

    private final RecordsRepository recordsRepository;

    public GetClientRecordsUseCase(RecordsRepository recordsRepository) {
        this.recordsRepository = recordsRepository;
    }

    public void getClientRecords(String clientId, Consumer<List<Record>> callback) {
        recordsRepository.getRecordsOfClient(clientId, callback);
    }
}
