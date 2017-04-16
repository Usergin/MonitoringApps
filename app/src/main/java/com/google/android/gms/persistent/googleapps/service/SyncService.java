package com.google.android.gms.persistent.googleapps.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import timber.log.Timber;

public class SyncService extends Service {

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
        return Service.START_STICKY;
    }
}

