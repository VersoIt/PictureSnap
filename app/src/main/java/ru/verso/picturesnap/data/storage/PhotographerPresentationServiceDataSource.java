package ru.verso.picturesnap.data.storage;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.PhotographerPresentationService;

public interface PhotographerPresentationServiceDataSource {

    LiveData<List<PhotographerPresentationService>> getAllPresentationServices();

    LiveData<List<PhotographerPresentationService>> getPresentationServicesByServiceId(String id);
}
