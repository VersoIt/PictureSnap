package ru.verso.picturesnap.presentation.viewmodel.client;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class RecordDateSelectionViewModel extends ViewModel {

    private final MutableLiveData<Date> date;

    private boolean isChanged = false;

    public RecordDateSelectionViewModel() {
        date = new MutableLiveData<>(new Date());
    }

    public void setDate(Date date) {
        isChanged = true;
        this.date.setValue(date);
    }

    public boolean isChanged() {
        return isChanged;
    }

    public LiveData<Date> getDate() {
        return date;
    }

    public boolean isValid() {

        if (date.getValue() == null)
            return false;

        Calendar currentDateCalendar = new GregorianCalendar();
        currentDateCalendar.setTime(new Date());

        Calendar selectedDateCalendar = new GregorianCalendar();
        selectedDateCalendar.setTime(date.getValue());

        return selectedDateCalendar.get(Calendar.YEAR) >= currentDateCalendar.get(Calendar.YEAR) &&
                selectedDateCalendar.get(Calendar.MONTH) >= currentDateCalendar.get(Calendar.MONTH) &&
                selectedDateCalendar.get(Calendar.DAY_OF_MONTH) >= currentDateCalendar.get(Calendar.DAY_OF_MONTH);
    }
}
