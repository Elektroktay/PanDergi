package com.gmail.senokt16.pandergi;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class PanDergi extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
