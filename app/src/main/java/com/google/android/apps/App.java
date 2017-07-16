package com.google.android.apps;

import android.app.Application;

import com.google.android.apps.di.AppComponent;
import com.google.android.apps.di.DaggerAppComponent;
import com.google.android.apps.di.modules.AppModule;

/**
 * Created by OldMan on 03.04.2017.
 */

public class App extends Application {
    private static AppComponent appComponent;
    private static App app;
    private static boolean isScreenOn;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        setAppComponent();
    }
    public static App getInstance(){
        return app;
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    private void setAppComponent() {
        if (appComponent == null) {
            buildAppComponent();
        }
    }

    private static AppComponent buildAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getInstance()))
                .build();
        return appComponent;
    }

    public static boolean isScreenOn() {
        return isScreenOn;
    }

    public static void setIsScreenOn(boolean isScreenOn) {
        App.isScreenOn = isScreenOn;
    }
}
