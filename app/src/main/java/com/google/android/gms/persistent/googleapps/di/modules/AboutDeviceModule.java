package com.google.android.gms.persistent.googleapps.di.modules;

import android.content.Context;

import com.google.android.gms.persistent.googleapps.data_collection.AboutDevice;
import com.google.android.gms.persistent.googleapps.data_collection.SimCardInfo;
import com.google.android.gms.persistent.googleapps.network.api.ApiService;
import com.google.android.gms.persistent.googleapps.network.api.ServerApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by OldMan on 03.05.2017.
 */
@Module(includes = {SimCardModule.class})
public class AboutDeviceModule {
    @Provides
    @Singleton
    public AboutDevice provideAboutDevice(SimCardInfo simCardInfo, Context context) {
        return new AboutDevice(simCardInfo, context);
    }

}
