package ru.verso.picturesnap.presentation.app;

import android.app.Application;

import com.yandex.mapkit.MapKitFactory;

public class PictureSnapApp extends Application {

    public static String STANDARD_DATE_FORMAT = "dd/MM/yyyy";
    public static final String DIGIT_MATCH = "^-?\\d+$";
    private static final String YANDEX_MAP_API = "256bf53d-3528-484c-b778-afe3fcb956df";
    public static final String FIREBASE_PHOTOGRAPHER_PATH = "photographers";
    public static final String FIREBASE_USERS_PATH = "users";
    public static final String SERVICE_PATH = "services";
    public static final String SERVICE_PROVISIONS_PATH = "service_provisions";
    public static final String FIREBASE_CLIENT_PATH = "clients";
    public static final String FIREBASE_FEEDBACKS_PATH = "feedbacks";
    public static final String UNSELECTED_IMAGE_PHOTOGRAPHER_PATH = "avatars/photographer/unselected.jpg";
    public static final String UNSELECTED_IMAGE_CLIENT_PATH = "avatars/customer/unselected.jpg";

    public static final int PHOTOGRAPH_IN_CITY_LIST_LIMIT = 5;

    public static final int PASSWORD_MIN_LENGTH = 8;

    @Override
    public void onCreate() {
        super.onCreate();

        MapKitFactory.setApiKey(YANDEX_MAP_API);
    }
}
