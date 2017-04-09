package com.google.android.gms.persistent.googleapps.di.modules;

import com.google.android.gms.persistent.googleapps.network.api.ApiService;
import com.google.android.gms.persistent.googleapps.network.api.ServerApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by OldMan on 08.04.2017.
 */

@Module(includes = {ApiModule.class})
public class ApiServiceModule {
    @Provides
    @Singleton
    public ApiService provideApiService(ServerApi serverApi) {
        return new ApiService(serverApi);
    }
}
