package ru.verso.picturesnap.presentation.viewmodel.client;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalTime;

public class RecordTimeSelectionViewModel extends ViewModel {

    private final MutableLiveData<LocalTime> time;

    private boolean isChanged = false;

    public RecordTimeSelectionViewModel() {
        time = new MutableLiveData<>(LocalTime.of(12, 0, 0));
    }

    public boolean isChanged() {
        return isChanged;
    }

    public LiveData<LocalTime> getTime() {
        return time;
    }

    public boolean isValid() {
        return time.getValue() != null;
    }

    public void setTime(LocalTime time) {
        isChanged = true;
        this.time.setValue(time);
    }
}
