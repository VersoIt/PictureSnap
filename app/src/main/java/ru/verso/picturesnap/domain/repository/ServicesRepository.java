package ru.verso.picturesnap.domain.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.PhotographerService;

public interface ServicesRepository {

    LiveData<List<PhotographerService>> getAllServices();
}
