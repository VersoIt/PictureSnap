package ru.verso.picturesnap.domain.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.PortfolioImage;

public interface ImagesRepository {

    LiveData<List<PortfolioImage>> getImagesByServiceProvisionId(int id);
}
