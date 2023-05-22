package ru.verso.picturesnap.domain.repository;

import android.net.Uri;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.PortfolioImage;

public interface PhotographerPortfolioPicturesRepository {

    void pushNewImage(Uri picture, String photographerServiceId);

    LiveData<List<PortfolioImage>> getAllImagesOf(String photographerServiceId);
}
