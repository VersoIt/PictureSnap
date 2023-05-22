package ru.verso.picturesnap.domain.usecase;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.PortfolioImage;
import ru.verso.picturesnap.domain.repository.PhotographerPortfolioPicturesRepository;

public class GetPhotographerPicturesUseCase {

    private final PhotographerPortfolioPicturesRepository photographerPortfolioPicturesrepository;

    public GetPhotographerPicturesUseCase(PhotographerPortfolioPicturesRepository photographerPortfolioPicturesrepository) {
        this.photographerPortfolioPicturesrepository = photographerPortfolioPicturesrepository;
    }

    public LiveData<List<PortfolioImage>> getPicturesOf(String photographerServiceProvisionId) {
        return photographerPortfolioPicturesrepository.getAllImagesOf(photographerServiceProvisionId);
    }
}
