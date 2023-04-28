package ru.verso.picturesnap.domain.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.models.PhotographService;
import ru.verso.picturesnap.domain.models.WorkingDay;

public interface PhotographRepository {

    LiveData<List<Photograph>> getPhotographsByLocation(String location);

    LiveData<List<PhotographService>> getAllPhotographServices();

    LiveData<Photograph> getPhotographById(int id);
}
