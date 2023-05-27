package ru.verso.picturesnap.presentation.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.verso.picturesnap.domain.usecase.GetClientDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerRecordsUseCase.GetPhotographerRecordsUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.UpdateRecordsDataUseCase;
import ru.verso.picturesnap.presentation.viewmodel.photographer.PhotographerRecordsViewModel;


public class PhotographerRecordsViewModelFactory implements ViewModelProvider.Factory {

    private final GetPhotographerDataUseCase getPhotographerDataUseCase;
    private final GetPhotographerRecordsUseCase getPhotographerRecordsUseCase;
    private final GetUserDataUseCase getUserDataUseCase;

    private final GetClientDataUseCase getClientDataUseCase;

    private final UpdateRecordsDataUseCase updateRecordsDataUseCase;

    public PhotographerRecordsViewModelFactory(GetPhotographerDataUseCase getPhotographerDataUseCase,
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

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PhotographerRecordsViewModel(getPhotographerDataUseCase, getPhotographerRecordsUseCase, getUserDataUseCase, getClientDataUseCase, updateRecordsDataUseCase);
    }
}