package com.google.android.apps.di.modules;


import com.google.android.apps.repositories.network.api.ServerApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by OldMan on 03.04.2017.
 */
@Module(includes = {RetrofitModule.class})
public class ApiModule {
    @Provides
    @Singleton
    public ServerApi provideServerApi(Retrofit retrofit) {
        return retrofit.create(ServerApi.class);
    }
}
