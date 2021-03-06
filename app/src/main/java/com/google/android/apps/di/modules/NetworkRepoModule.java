package com.google.android.apps.di.modules;

import com.google.android.apps.repositories.network.NetworkRepo;
import com.google.android.apps.repositories.network.NetworkRepoImpl;
import com.google.android.apps.repositories.network.api.ApiService;
import com.google.android.apps.utils.Preferences;

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
    public NetworkRepo provideNetworkRepo(ApiService driverService, Preferences preferences) {
        return new NetworkRepoImpl(driverService, preferences);
    }
}
