package ru.verso.picturesnap.presentation.viewmodel.unregistered;

import android.annotation.SuppressLint;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ru.verso.picturesnap.R;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.domain.usecase.GetServicesUseCase;

public class PhotographerServicesSelectionViewModel extends ViewModel {

    private final MutableLiveData<List<PhotographerPresentationService>> allServices;

    private final MutableLiveData<Integer> statusStringId = new MutableLiveData<>(R.string.select_service_prices);

    @SuppressLint("DiscouragedApi")
    public PhotographerServicesSelectionViewModel(LifecycleOwner viewLifeCycleOwner, GetServicesUseCase getServicesUseCase) {

        this.allServices = new MutableLiveData<>(new ArrayList<>());

        getServicesUseCase.getAllServices().observe(viewLifeCycleOwner, services -> allServices.setValue(services.stream().map(s -> {
            PhotographerPresentationService photographerPresentationService = new PhotographerPresentationService();
            photographerPresentationService.setServiceId(s.getId());
            photographerPresentationService.setName(s.getName());

            return photographerPresentationService;
        }).collect(Collectors.toList())));
    }

    public LiveData<List<PhotographerPresentationService>> getAllServices() {
        return allServices;
    }

    public void setServices(List<PhotographerPresentationService> services) {
        allServices.setValue(services);
    }

    public void setStatus(int id) {
        statusStringId.setValue(id);
    }

    public LiveData<Integer> getStatusId() {
        return statusStringId;
    }
}
