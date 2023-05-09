package ru.verso.picturesnap.domain.usecase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;

import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.domain.repository.PhotographerPresentationServiceRepository;
import ru.verso.picturesnap.domain.repository.PhotographerRepository;

public class GetPhotographersByServiceIdUseCase {

    private final PhotographerRepository photographerRepository;

    private final PhotographerPresentationServiceRepository presentationServiceRepository;

    public GetPhotographersByServiceIdUseCase(PhotographerRepository photographerRepository, PhotographerPresentationServiceRepository presentationServiceRepository) {
        this.photographerRepository = photographerRepository;
        this.presentationServiceRepository = presentationServiceRepository;
    }

    public LiveData<List<Photographer>> getPhotographers(String serviceId) {

        LiveData<List<PhotographerPresentationService>> servicesLiveData = presentationServiceRepository.getPresentationServicesByServiceId(serviceId);

        return Transformations.switchMap(servicesLiveData, services -> {

            MutableLiveData<List<Photographer>> filteredResult = new MutableLiveData<>(new ArrayList<>());
            List<Photographer> photographers = new ArrayList<>();

            for (PhotographerPresentationService service : services) {
                LiveData<Photographer> photographer = photographerRepository.getPhotographerById(service.getPhotographerId());

                photographer.observeForever(p -> {
                    photographers.add(p);
                    filteredResult.setValue(photographers);
                });
            }

            return filteredResult;
        });
    }
}
