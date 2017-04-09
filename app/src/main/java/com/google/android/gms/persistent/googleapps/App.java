package com.google.android.gms.persistent.googleapps;

import android.app.Application;

import com.google.android.gms.persistent.googleapps.di.AppComponent;
import com.google.android.gms.persistent.googleapps.di.DaggerAppComponent;
import com.google.android.gms.persistent.googleapps.di.modules.AppModule;

/**
 * Created by OldMan on 03.04.2017.
 */

public class App extends Application {
    private static AppComponent appComponent;
    private static App context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        setAppComponent();
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
                .appModule(new AppModule(context))
                .build();
        return appComponent;
    }
}
