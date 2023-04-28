package ru.verso.picturesnap.domain.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.models.PhotographService;

public interface PhotographRepository {

    LiveData<List<Photograph>> getAllPhotographs();

    LiveData<List<PhotographService>> getAllPhotographServices();

    LiveData<Photograph> getPhotographById(int id);
}
