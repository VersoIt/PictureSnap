package ru.verso.picturesnap.presentation.app;

import android.app.Application;

import com.yandex.mapkit.MapKitFactory;

public class PictureSnapApp extends Application {

    public static String STANDARD_DATE_FORMAT = "dd/MM/yyyy";

    public static String LONG_DATE_FORMAT = "dd MMMM, hh:mm";
    public static final String DIGIT_MATCH = "^-?\\d+$";
    private static final String YANDEX_MAP_API = "256bf53d-3528-484c-b778-afe3fcb956df";

    public static final int PHOTOGRAPH_IN_CITY_LIST_LIMIT = 5;

    public static final int PASSWORD_MIN_LENGTH = 8;

    @Override
    public void onCreate() {
        super.onCreate();

        MapKitFactory.setApiKey(YANDEX_MAP_API);
    }
}
