package com.google.android.apps.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.google.android.apps.App;
import com.google.android.apps.utils.Preferences;

import java.util.Calendar;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by OldMan on 08.04.2017.
 */
@Module
public class AppModule {
    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return app.getApplicationContext();
    }

    @Provides
    @Singleton
    public SharedPreferences providesPreference() {
        return PreferenceManager.getDefaultSharedPreferences(app);
    }

    @Provides
    @Singleton
    public Resources providesResources() {
        return app.getApplicationContext().getResources();
    }

    @Provides
    @Singleton
    public Calendar providesCalendar() {
        return Calendar.getInstance();
    }

    @Provides
    @Singleton
    public Preferences providesMyPreference() {
        return new Preferences(app);
    }

}