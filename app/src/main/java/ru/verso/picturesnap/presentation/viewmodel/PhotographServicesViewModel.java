package ru.verso.picturesnap.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.verso.picturesnap.data.repository.ClientRepositoryImpl;
import ru.verso.picturesnap.domain.models.PhotographService;
import ru.verso.picturesnap.domain.repository.ClientRepository;

public class PhotographServicesViewModel extends AndroidViewModel {

    private final LiveData<List<PhotographService>> services;

    public PhotographServicesViewModel(@NonNull Application application) {
        super(application);
        ClientRepository repository = new ClientRepositoryImpl(application);
        services = repository.getPhotographServices();
    }

    public LiveData<List<PhotographService>> getServices() {
        return services;
    }
}
