package com.google.android.gms.persistent.googleapps.di.modules;

import android.content.Context;

import com.google.android.gms.persistent.googleapps.data_collection.AboutDevice;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by OldMan on 03.05.2017.
 */
@Module
public class AboutDeviceModule {
    @Provides
    @Singleton
    public AboutDevice provideAboutDevice(Context context) {
        return new AboutDevice(context);
    }

}
