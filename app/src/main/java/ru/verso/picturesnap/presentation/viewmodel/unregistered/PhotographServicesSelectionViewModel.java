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
import ru.verso.picturesnap.domain.models.PhotographPresentationService;
import ru.verso.picturesnap.domain.usecase.GetServicesUseCase;

public class PhotographServicesSelectionViewModel extends ViewModel {

    private final MutableLiveData<List<PhotographPresentationService>> allServices;

    private final MutableLiveData<Integer> statusStringId = new MutableLiveData<>(R.string.select_service_prices);

    @SuppressLint("DiscouragedApi")
    public PhotographServicesSelectionViewModel(LifecycleOwner viewLifeCycleOwner, GetServicesUseCase getServicesUseCase) {

        this.allServices = new MutableLiveData<>(new ArrayList<>());

        getServicesUseCase.getAllServices().observe(viewLifeCycleOwner, services -> allServices.setValue(services.stream().map(s -> {
            PhotographPresentationService photographPresentationService = new PhotographPresentationService();
            photographPresentationService.setServiceId(s.getId());
            photographPresentationService.setName(s.getName());

            return photographPresentationService;
        }).collect(Collectors.toList())));
    }

    public LiveData<List<PhotographPresentationService>> getAllServices() {
        return allServices;
    }

    public void setServices(List<PhotographPresentationService> services) {
        allServices.setValue(services);
    }

    public void setStatus(int id) {
        statusStringId.setValue(id);
    }

    public LiveData<Integer> getStatusId() {
        return statusStringId;
    }
}
