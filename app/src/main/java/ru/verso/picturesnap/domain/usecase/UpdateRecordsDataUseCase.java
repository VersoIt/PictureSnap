package ru.verso.picturesnap.domain.usecase;

import ru.verso.picturesnap.domain.models.Record;
import ru.verso.picturesnap.domain.repository.RecordsRepository;

public class UpdateRecordsDataUseCase {

    private final RecordsRepository recordsRepository;

    public UpdateRecordsDataUseCase(RecordsRepository recordsRepository) {
        this.recordsRepository = recordsRepository;
    }

    public void updateRecordStatus(String recordId, Record.Status status) {
        recordsRepository.updateRecordStatus(recordId, status);
    }
}
