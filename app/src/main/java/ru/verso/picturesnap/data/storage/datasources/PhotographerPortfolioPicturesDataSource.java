package ru.verso.picturesnap.data.storage.datasources;

import android.net.Uri;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.PortfolioImage;

public interface PhotographerPortfolioPicturesDataSource {

    void pushNewImage(Uri picture, String photographerServiceId);

    LiveData<List<PortfolioImage>> getAllImagesOf(String photographerServiceId);
}
