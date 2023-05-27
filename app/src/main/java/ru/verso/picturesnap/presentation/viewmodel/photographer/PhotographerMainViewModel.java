package ru.verso.picturesnap.presentation.viewmodel.photographer;

import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.function.Consumer;

import ru.verso.picturesnap.domain.models.Record;
import ru.verso.picturesnap.domain.usecase.GetPhotographerRecordsUseCase.GetPhotographerRecordsUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;

public class PhotographerMainViewModel extends ViewModel {

    private final GetPhotographerRecordsUseCase getPhotographerRecordsUseCase;

    private final GetUserDataUseCase getUserDataUseCase;

    public PhotographerMainViewModel(GetPhotographerRecordsUseCase getPhotographerRecordsUseCase,
                                     GetUserDataUseCase getUserDataUseCase) {
        this.getPhotographerRecordsUseCase = getPhotographerRecordsUseCase;
        this.getUserDataUseCase = getUserDataUseCase;
    }

    public void getPhotographerRecords(Consumer<List<Record>> callback) {
        getPhotographerRecordsUseCase.getPhotographerRecords(getUserDataUseCase.getUserId(), callback);
    }
}
