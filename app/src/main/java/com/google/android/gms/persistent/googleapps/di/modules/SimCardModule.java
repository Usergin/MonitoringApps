package com.google.android.gms.persistent.googleapps.di.modules;

import android.content.Context;

import com.google.android.gms.persistent.googleapps.data_collection.SimCardInfo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by OldMan on 03.05.2017.
 */
@Module(includes = {AppModule.class})
public class SimCardModule {
    @Provides
    @Singleton
    public SimCardInfo provideSimCardInfo(Context context) {
        return new SimCardInfo(context);
    }

}
