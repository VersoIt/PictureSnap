package ru.verso.picturesnap.data.repository;

import android.net.Uri;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.data.storage.datasources.PhotographerPortfolioPicturesDataSource;
import ru.verso.picturesnap.domain.models.PortfolioImage;
import ru.verso.picturesnap.domain.repository.PhotographerPortfolioPicturesRepository;

public class PhotographerPortfolioPicturesRepositoryImpl implements PhotographerPortfolioPicturesRepository {

    private final PhotographerPortfolioPicturesDataSource photographerPortfolioPicturesDataSource;

    public PhotographerPortfolioPicturesRepositoryImpl(PhotographerPortfolioPicturesDataSource photographerPortfolioPicturesDataSource) {
        this.photographerPortfolioPicturesDataSource = photographerPortfolioPicturesDataSource;
    }

    @Override
    public void pushNewImage(Uri picture, String photographerServiceId) {
        photographerPortfolioPicturesDataSource.pushNewImage(picture, photographerServiceId);
    }

    @Override
    public LiveData<List<PortfolioImage>> getAllImagesOf(String photographerServiceId) {
        return photographerPortfolioPicturesDataSource.getAllImagesOf(photographerServiceId);
    }
}
