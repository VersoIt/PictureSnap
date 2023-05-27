package ru.verso.picturesnap.domain.usecase.GetPhotographerRecordsUseCase;

import java.util.List;
import java.util.function.Consumer;

import ru.verso.picturesnap.domain.models.Record;
import ru.verso.picturesnap.domain.repository.RecordsRepository;

public class GetPhotographerRecordsUseCase {

    private final RecordsRepository recordsRepository;

    public GetPhotographerRecordsUseCase(RecordsRepository recordsRepository) {
        this.recordsRepository = recordsRepository;
    }

    public void getPhotographerRecords(String photographerId, Consumer<List<Record>> callback) {
        recordsRepository.getRecordsOfPhotographer(photographerId, callback);
    }
}
