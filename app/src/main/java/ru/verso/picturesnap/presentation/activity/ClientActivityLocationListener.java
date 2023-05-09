package ru.verso.picturesnap.presentation.activity;

import android.location.Location;

import androidx.annotation.NonNull;

public class ClientActivityLocationListener implements android.location.LocationListener {

    public LocationKeeper locationKeeper;

    public ClientActivityLocationListener(LocationKeeper citySave) {
        this.locationKeeper = citySave;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        locationKeeper.saveLocation(location.getLatitude(),
                location.getLongitude());
    }

}
