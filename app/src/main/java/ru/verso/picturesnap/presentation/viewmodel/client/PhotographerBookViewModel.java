package ru.verso.picturesnap.presentation.viewmodel.client;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ru.verso.picturesnap.domain.models.PhotographerPresentationService;
import ru.verso.picturesnap.domain.usecase.BookPhotographerUseCase;
import ru.verso.picturesnap.domain.usecase.GetPhotographerDataUseCase;

public class PhotographerBookViewModel extends ViewModel {

    private String clientId;

    private final MutableLiveData<PhotographerPresentationService> service;

    private final BookPhotographerUseCase bookPhotographerUseCase;

    private final GetPhotographerDataUseCase getPhotographerDataUseCase;

    private String photographerId;

    private final MutableLiveData<Integer> selectedServicePosition;

    public PhotographerBookViewModel(BookPhotographerUseCase bookPhotographerUseCase, GetPhotographerDataUseCase getPhotographerDataUseCase) {
        this.bookPhotographerUseCase = bookPhotographerUseCase;
        this.getPhotographerDataUseCase = getPhotographerDataUseCase;
        this.service = new MutableLiveData<>();

        selectedServicePosition = new MutableLiveData<>(0);
    }

    public void updateSelectedServicePosition(int position) {
        selectedServicePosition.setValue(position);
    }

    public LiveData<Integer> getSelectedServicePosition() {
        return selectedServicePosition;
    }

    public void putPhotographerId(String photographerId) {
        this.photographerId = photographerId;
    }

    public void putClientId(String id) {
        clientId = id;
    }

    public void setService(PhotographerPresentationService service) {
        this.service.setValue(service);
    }

    public boolean book(Date selectedDay, LocalTime selectedTime, String comment) {

        Calendar calendar = new GregorianCalendar();
        Date currentDate = new Date();

        calendar.setTime(selectedDay);
        calendar.set(Calendar.HOUR_OF_DAY, selectedTime.getHour());
        calendar.set(Calendar.MINUTE, selectedTime.getMinute());

        if (calendar.getTime().compareTo(currentDate) <= 0)
            return false;

        if (service.getValue() != null) {
            bookPhotographerUseCase.book(service.getValue().getId(), clientId, photographerId, calendar.getTime(), comment);
        }

        return true;
    }

    public LiveData<List<PhotographerPresentationService>> getServicesOfPhotographer() {
        return getPhotographerDataUseCase.getPhotographerServicesById(photographerId);
    }
}
