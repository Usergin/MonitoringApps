package com.google.android.apps.di;

import android.content.Context;
import android.content.res.Resources;


import com.google.android.apps.data_collection.AboutDevice;
import com.google.android.apps.di.modules.AboutDeviceModule;
import com.google.android.apps.di.modules.ApiModule;
import com.google.android.apps.di.modules.ApiServiceModule;
import com.google.android.apps.di.modules.AppModule;
import com.google.android.apps.di.modules.NetworkRepoModule;
import com.google.android.apps.repositories.network.NetworkRepo;
import com.google.android.apps.repositories.network.api.ApiService;
import com.google.android.apps.utils.Preferences;

import java.util.Calendar;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by OldMan on 03.04.2017.
 */
@Singleton
@Component(modules = {AppModule.class, ApiModule.class,
        ApiServiceModule.class, NetworkRepoModule.class,
        AboutDeviceModule.class})
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
