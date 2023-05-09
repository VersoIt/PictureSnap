package ru.verso.picturesnap.domain.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.PhotographerPresentationService;

public interface PhotographerPresentationServiceRepository {

    LiveData<List<PhotographerPresentationService>> getAllPresentationServices();

    LiveData<List<PhotographerPresentationService>> getPresentationServicesByServiceId(String id);
}
