package com.google.android.gms.persistent.googleapps.di.modules;

import com.google.android.gms.persistent.googleapps.network.api.ApiService;
import com.google.android.gms.persistent.googleapps.repositories.NetworkRepo;
import com.google.android.gms.persistent.googleapps.repositories.NetworkRepoImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by OldMan on 08.04.2017.
 */
@Module(includes = {ApiServiceModule.class})
public class NetworkRepoModule {
    @Provides
    @Singleton
    public NetworkRepo provideNetworkRepo(ApiService driverService) {
        return new NetworkRepoImpl(driverService);
    }
}
