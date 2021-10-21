package com.example.skincap;

import android.app.Application;

import com.example.skincap.db.AppDatabase;

import dagger.hilt.android.HiltAndroidApp;

public class SkinCapApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppDatabase.setINSTANCE(this);
    }
}
