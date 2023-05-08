package ru.verso.picturesnap.presentation.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import ru.verso.picturesnap.domain.models.Location;

public class LocationCoordinator {

    public static String getCityNameByLocation(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        if (latitude == 0 && longitude == 0)
            return "";

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null) {
                Address address = addresses.get(0);
                String result = address.getLocality();
                return result == null ? "" : result;
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return "";
    }

    public static String getFullAddress(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        StringBuilder result = new StringBuilder();
        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if (addressList != null) {
                Address address = addressList.get(0);
                result.append(address.getThoroughfare());
                result.append(", ");
                result.append(address.getSubThoroughfare());
                if (result.toString().contains("null"))
                    return getCityNameByLocation(context, latitude, longitude);
                return result.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static LiveData<Location> getLocationByString(Context context, String location) {
        MutableLiveData<Location> mutableLiveData = new MutableLiveData<>();

        new Thread(() -> {
            Geocoder geocoder = new Geocoder(context);
            try {
                List<Address> addresses = geocoder.getFromLocationName(location, 1);
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    mutableLiveData.postValue(new Location(address.getLatitude(), address.getLongitude()));
                    return;
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            mutableLiveData.postValue(new Location(0, 0));
        }).start();

        return mutableLiveData;
    }
}
