package ru.verso.picturesnap.data.storage.datasources;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.PhotographerService;

public interface ServicesDataSource {

    LiveData<List<PhotographerService>> getAllServices();
}
