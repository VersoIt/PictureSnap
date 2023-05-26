package ru.verso.picturesnap.data.storage.datasources;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.PhotographerService;

public interface PhotographerServiceDataSource {

    LiveData<List<PhotographerService>> getAllServices();

    LiveData<PhotographerService> getServiceById(String id);
}
