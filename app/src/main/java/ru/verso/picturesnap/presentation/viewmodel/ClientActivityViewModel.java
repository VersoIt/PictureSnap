package ru.verso.picturesnap.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.verso.picturesnap.domain.models.Photograph;
import ru.verso.picturesnap.domain.repository.RoleRepository;
import ru.verso.picturesnap.domain.usecase.OperationPhotographDataUseCase;
import ru.verso.picturesnap.domain.usecase.OperationUserDataUseCase;

public class ClientActivityViewModel extends ViewModel {

    private final MutableLiveData<String> location;

    private final OperationUserDataUseCase operationUserDataUseCase;

    private final LiveData<List<Photograph>> photographsByLocation;

    public ClientActivityViewModel(OperationUserDataUseCase operationUserDataUseCase,
                                   OperationPhotographDataUseCase operationPhotographDataUseCase) {

        this.operationUserDataUseCase = operationUserDataUseCase;

        location = new MutableLiveData<>(operationUserDataUseCase.getLocation());
        photographsByLocation = operationPhotographDataUseCase.getPhotographsByLocation(location.getValue());
    }

    public void setLocation(String location) {
        operationUserDataUseCase.setLocation(location);
        this.location.setValue(location);
    }

    public LiveData<List<Photograph>> getPhotographsByLocation() {
        return photographsByLocation;
    }

    public boolean isFirst() {
        return operationUserDataUseCase.isFirstCome();
    }

    public void setVisited() {
        operationUserDataUseCase.setVisited();
    }

    public RoleRepository.Role getCurrentRole() {
        return operationUserDataUseCase.getCurrentRole();
    }
}