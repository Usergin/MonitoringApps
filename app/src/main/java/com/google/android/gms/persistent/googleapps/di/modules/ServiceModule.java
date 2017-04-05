package com.google.android.gms.persistent.googleapps.di.modules;

import com.google.android.gms.persistent.googleapps.network.api.ServerApi;
import com.google.android.gms.persistent.googleapps.network.api.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by OldMan on 03.04.2017.
 */
@Module(includes = {ApiModule.class})
public class ServiceModule {
    @Provides
    @Singleton
    public ApiService provideDriverService(ServerApi serverApi) {
        return new ApiService(serverApi);
    }
}
