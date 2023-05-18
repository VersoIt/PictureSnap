package ru.verso.picturesnap.presentation.viewmodel.client;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ru.verso.picturesnap.domain.models.Client;
import ru.verso.picturesnap.domain.models.Feedback;
import ru.verso.picturesnap.domain.models.Photographer;
import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.domain.usecase.GetClientDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;
import ru.verso.picturesnap.domain.usecase.GetUserDataUseCase;
import ru.verso.picturesnap.domain.usecase.SendFeedbackUseCase;

public class SendFeedbackViewModel extends ViewModel {

    private final SendFeedbackUseCase sendFeedbackUseCase;

    private final MutableLiveData<Integer> rating;

    private final GetClientDataUseCase getClientDataUseCase;

    private final GetPhotographerDataUseCase getPhotographerDataUseCase;

    private String destination;
    private final String source;

    public SendFeedbackViewModel(SendFeedbackUseCase sendFeedbackUseCase, GetUserDataUseCase getUserDataUseCase, GetClientDataUseCase getClientDataUseCase, GetPhotographerDataUseCase getPhotographerDataUseCase) {
        rating = new MutableLiveData<>(0);
        this.sendFeedbackUseCase = sendFeedbackUseCase;
        this.getClientDataUseCase = getClientDataUseCase;
        this.getPhotographerDataUseCase = getPhotographerDataUseCase;

        source = getUserDataUseCase.getUserId();
    }

    public void putPhotographerDestinationId(String id) {
        destination = id;
    }

    public void setRating(int value) {
        rating.setValue(value);
    }

    public LiveData<Integer> getRating() {
        return rating;
    }

    private Date getCurrentTime() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        return new Timestamp(gregorianCalendar.getTime().getTime());
    }

    private LiveData<Client> getSourceClient() {

        return getClientDataUseCase.getClientById(source);
    }

    private LiveData<Photographer> getDestinationPhotographer(String id) {
        return getPhotographerDataUseCase.getPhotographerById(id);
    }

    private LiveData<Feedback> getFeedbackToSend(String text) {

        LiveData<Photographer> photographerDestination = getPhotographerDataUseCase.getPhotographerById(destination);

        return Transformations.switchMap(photographerDestination, photographer -> {

            MutableLiveData<Feedback> result = new MutableLiveData<>();

            LiveData<Client> client = getClientDataUseCase.getClientById(source);
            client.observeForever(c -> {
                if (client.getValue() != null && client.getValue().getId() != null && photographer != null) {
                    Feedback feedback = new Feedback("", photographer.getId(), String.format("%s %s", client.getValue().getFirstName(), client.getValue().getLastName()), text, getCurrentTime());
                    if (rating.getValue() != null) {
                        feedback.setRating(rating.getValue());
                        result.setValue(feedback);
                    }
                }
            });

            return result;
        });
    }

    public boolean isEmpty(String text) {
        final int minTextLength = 16;
        if (rating.getValue() == null)
            return true;

        return rating.getValue() == 0 || text.isEmpty() || text.length() < minTextLength;
    }

    public void sendFeedback(String text) {
        getFeedbackToSend(text).observeForever(sendFeedbackUseCase::send);
    }
}