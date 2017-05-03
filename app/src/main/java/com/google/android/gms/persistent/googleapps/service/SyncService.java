package com.google.android.gms.persistent.googleapps.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.android.gms.persistent.googleapps.App;
import com.google.android.gms.persistent.googleapps.repositories.NetworkRepo;
import com.google.android.gms.persistent.googleapps.utils.Preferences;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import timber.log.Timber;

public class SyncService extends Service {

    @Inject
    NetworkRepo networkRepo;
    @Inject
    Preferences preferences;
    public SyncService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.tag("SyncService");
        super.onStartCommand(intent, flags, startId);
        networkRepo = App.getAppComponent().getNetworkRepo();
        preferences = App.getAppComponent().getPreferences();
        onSyncSettings();
        return Service.START_STICKY;
    }

//    @Override
    public void onSyncSettings() {
        networkRepo.onStateSync()
                .repeatWhen(done -> done.delay(10, TimeUnit.SECONDS))
                .retryWhen(throwableFlowable -> throwableFlowable.delay(10, TimeUnit.SECONDS))
                .doOnNext(syncResponse -> preferences.setWorkSetup(syncResponse.getSettings()))
                .subscribe(subscriber -> handleSuccessLoadSettings(subscriber.getCode()),
                        this::handleErrorLoadSettings);
      }
    private void handleSuccessLoadSettings(
            @NonNull int status) {
        Timber.d("Sync is " + status);
    }

    private void handleErrorLoadSettings(Throwable throwable) {
        Timber.d("Sync is " + throwable);
    }
}

