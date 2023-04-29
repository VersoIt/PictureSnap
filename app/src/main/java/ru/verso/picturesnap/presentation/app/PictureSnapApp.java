package ru.verso.picturesnap.presentation.app;

import android.app.Application;

import com.yandex.mapkit.MapKitFactory;

public class PictureSnapApp extends Application {

    private static final String YANDEX_MAP_API = "256bf53d-3528-484c-b778-afe3fcb956df";

    @Override
    public void onCreate() {
        super.onCreate();

        MapKitFactory.setApiKey(YANDEX_MAP_API);
    }
}
