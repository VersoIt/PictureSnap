package ru.verso.picturesnap.presentation.viewmodel.client;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.function.Consumer;

import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.domain.models.Record;
import ru.verso.picturesnap.domain.usecase.GetClientRecordsUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;

public class ClientMyRecordsViewModel extends ViewModel {

    private final GetPhotographerDataUseCase getPhotographerDataUseCase;
    private final GetClientRecordsUseCase getClientRecordsUseCase;
    private final GetUserDataUseCase getUserDataUseCase;

    public ClientMyRecordsViewModel(GetPhotographerDataUseCase getPhotographerDataUseCase,
                                    GetClientRecordsUseCase getClientRecordsUseCase,
                                    GetUserDataUseCase getUserDataUseCase) {

        this.getPhotographerDataUseCase = getPhotographerDataUseCase;
        this.getClientRecordsUseCase = getClientRecordsUseCase;
        this.getUserDataUseCase = getUserDataUseCase;
    }

    public LiveData<Photographer> getPhotographerById(String id) {
        return getPhotographerDataUseCase.getPhotographerById(id);
    }

    public LiveData<PhotographerPresentationService> getServiceById(String serviceId) {
        return getPhotographerDataUseCase.getServiceById(serviceId);
    }

    public void getServicesOfClient(Consumer<List<Record>> callback) {
        getClientRecordsUseCase.getClientRecords(getUserDataUseCase.getUserId(), callback);
    }
}
