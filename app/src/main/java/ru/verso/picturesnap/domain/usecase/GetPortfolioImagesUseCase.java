package ru.verso.picturesnap.domain.usecase;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.PortfolioImage;
import ru.verso.picturesnap.domain.repository.ImagesRepository;

public class GetPortfolioImagesUseCase {

    private final ImagesRepository imagesRepository;

    public GetPortfolioImagesUseCase(ImagesRepository imagesRepository) {
        this.imagesRepository = imagesRepository;
    }

    public LiveData<List<PortfolioImage>> getImagesByServiceProvisionId(int id) {
        return imagesRepository.getImagesByServiceProvisionId(id);
    }
}
