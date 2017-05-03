package com.google.android.gms.persistent.googleapps.di;

import android.content.Context;
import android.content.res.Resources;

import com.google.android.gms.persistent.googleapps.data_collection.AboutDevice;
import com.google.android.gms.persistent.googleapps.di.modules.AboutDeviceModule;
import com.google.android.gms.persistent.googleapps.di.modules.ApiModule;
import com.google.android.gms.persistent.googleapps.di.modules.ApiServiceModule;
import com.google.android.gms.persistent.googleapps.di.modules.AppModule;
import com.google.android.gms.persistent.googleapps.di.modules.NetworkRepoModule;
import com.google.android.gms.persistent.googleapps.di.modules.SimCardModule;
import com.google.android.gms.persistent.googleapps.network.api.ApiService;
import com.google.android.gms.persistent.googleapps.repositories.NetworkRepo;
import com.google.android.gms.persistent.googleapps.utils.Preferences;

import java.util.Calendar;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by OldMan on 03.04.2017.
 */
@Singleton
@Component(modules = {AppModule.class, ApiModule.class,
        ApiServiceModule.class, NetworkRepoModule.class,
        SimCardModule.class, AboutDeviceModule.class})
public interface AppComponent {

    Context getContext();

    Preferences getPreferences();

    Resources getResources();

    Calendar getCalendar();

    ApiService getApiService();

    NetworkRepo getNetworkRepo();

    AboutDevice getAboutDevice();

//    void inject(MainInteractor interactor);
//    void inject(MainPresenter presenter);
//    void inject(BootBroadcastReceiver bootBroadcastReceiver);
//    void inject(SmsBroadcastReceiver smsBroadcastReceiver);
//    void inject(WorkTimeDefiner workTimeDefiner);
//    void inject(CallReceiver callReceiver);
}
