package ru.verso.picturesnap.presentation.viewmodel.unregistered;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.domain.models.PortfolioImage;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerPicturesUseCase;
import ru.verso.picturesnap.domain.usecase.SendPhotographerPicturesUseCase;

public class ServicesViewModel extends ViewModel {

    private final GetPhotographerDataUseCase getPhotographerDataUseCase;

    private final GetPhotographerPicturesUseCase getPhotographerPicturesUseCase;

    private final SendPhotographerPicturesUseCase sendPhotographerPicturesUseCase;

    private String photographerId;

    public ServicesViewModel(GetPhotographerDataUseCase getPhotographerDataUseCase, GetPhotographerPicturesUseCase getPhotographerPicturesUseCase, SendPhotographerPicturesUseCase sendPhotographerPicturesUseCase) {
        this.getPhotographerDataUseCase = getPhotographerDataUseCase;
        this.getPhotographerPicturesUseCase = getPhotographerPicturesUseCase;
        this.sendPhotographerPicturesUseCase = sendPhotographerPicturesUseCase;
    }

    public void putPhotographerId(String photographerId) {
        this.photographerId = photographerId;
    }

    public LiveData<List<PhotographerPresentationService>> getPhotographerServices() {
        return getPhotographerDataUseCase.getPhotographerServicesById(photographerId);
    }

    public void loadPicture(Uri picture, String servicePresentationId) {
        sendPhotographerPicturesUseCase.send(picture,servicePresentationId);
    }

    public LiveData<List<PortfolioImage>> getPicturesOf(String servicePresentationId) {
        return getPhotographerPicturesUseCase.getPicturesOf(servicePresentationId);
    }
}
