package ru.verso.picturesnap.presentation.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationCoordinator {

    public static String getCityNameByLocation(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null) {
                Address address = addresses.get(0);
                return address.getLocality();
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
                return result.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
