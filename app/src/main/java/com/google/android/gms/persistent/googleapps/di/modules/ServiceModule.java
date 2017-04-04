package com.google.android.gms.persistent.googleapps.di.modules;

import com.google.android.gms.persistent.googleapps.api.ServerApi;
import com.google.android.gms.persistent.googleapps.api.ServerService;

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
    public ServerService provideDriverService(ServerApi serverApi) {
        return new ServerService(serverApi);
    }
}
