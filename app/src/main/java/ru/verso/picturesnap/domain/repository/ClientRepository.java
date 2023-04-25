package ru.verso.picturesnap.domain.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.domain.models.PhotographService;

public interface ClientRepository {
    LiveData<List<PhotographService>> getPhotographServices();
}
