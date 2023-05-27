package ru.verso.picturesnap.presentation.viewmodel.photographer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.function.Consumer;

import ru.verso.picturesnap.domain.models.Client;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.domain.models.Record;
import ru.verso.picturesnap.domain.usecase.GetClientDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerRecordsUseCase.GetPhotographerRecordsUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateRecordsDataUseCase;
import ru.verso.picturesnap.presentation.adapters.photographer.RecordViewHolder;

public class PhotographerRecordsViewModel extends ViewModel implements RecordViewHolder.RecordStatusChanger {

    private final GetPhotographerDataUseCase getPhotographerDataUseCase;

    private final GetPhotographerRecordsUseCase getPhotographerRecordsUseCase;

    private final GetUserDataUseCase getUserDataUseCase;

    private final GetClientDataUseCase getClientDataUseCase;

    private final UpdateRecordsDataUseCase updateRecordsDataUseCase;

    public PhotographerRecordsViewModel(GetPhotographerDataUseCase getPhotographerDataUseCase,
                                        GetPhotographerRecordsUseCase getPhotographerRecordsUseCase,
                                        GetUserDataUseCase getUserDataUseCase,
                                        GetClientDataUseCase getClientDataUseCase,
                                        UpdateRecordsDataUseCase updateRecordsDataUseCase) {

        this.getPhotographerDataUseCase = getPhotographerDataUseCase;
        this.getPhotographerRecordsUseCase = getPhotographerRecordsUseCase;
        this.getUserDataUseCase = getUserDataUseCase;
        this.getClientDataUseCase = getClientDataUseCase;
        this.updateRecordsDataUseCase = updateRecordsDataUseCase;
    }

    public LiveData<Client> getClientById(String id) {
        return getClientDataUseCase.getClientById(id);
    }

    public LiveData<PhotographerPresentationService> getServiceById(String serviceId) {
        return getPhotographerDataUseCase.getServiceById(serviceId);
    }

    public void getServicesOfPhotographer(Consumer<List<Record>> callback) {
        getPhotographerRecordsUseCase.getPhotographerRecords(getUserDataUseCase.getUserId(), callback);
    }

    @Override
    public void changeRecordStatusTo(String recordId, Record.Status status) {
        updateRecordsDataUseCase.updateRecordStatus(recordId, status);
    }
}
