package ru.verso.picturesnap.domain.repository;

import android.net.Uri;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.domain.models.PhotographerService;

public interface PhotographerRepository {

    LiveData<List<Photographer>> getAllPhotographers();

    LiveData<List<PhotographerService>> getAllPhotographerServices();

    LiveData<Photographer> getPhotographerById(String id);

    LiveData<List<PhotographerPresentationService>> getPhotographerServicesById(String photographerId);

    void updatePhotographerAvatar(String photographerId, Uri uri);

    void updateRatingOf(String photographerId, float lastAverage, int lastCount, int newAdd);
}