package com.google.android.apps.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.apps.App;
import com.google.android.apps.utils.Preferences;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.patloew.rxlocation.RxLocation;

import java.util.Calendar;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LocationService extends Service {
    private final String TAG = LocationService.class.getSimpleName();
    private static final int SERVICE_REQUEST_CODE = 15;

    protected LocationManager locationManager;
    private Preferences preferences;
    private RxLocation rxLocation;
    private LocationRequest locationRequest;

    @Override
    public void onCreate() {
        preferences = App.getAppComponent().getPreferences();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand " + preferences.isLocation());
        if (preferences.isLocation()) {
            rxLocation = new RxLocation(this);
            startTracker();
        }

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 15);
        PendingIntent servicePendingIntent = PendingIntent.getService(this,
                SERVICE_REQUEST_CODE, new Intent(this, LocationService.class),// SERVICE_REQUEST_CODE
                PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                servicePendingIntent);
        super.onStartCommand(intent, flags, startId);
        return Service.START_STICKY;
    }

    private void startTracker() {
        try {
            int locationMode = preferences.getLocationMode();
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Log.d(TAG, "locationMode " + locationMode);

            // getting GPS status
//            boolean isGPSEnabled = locationManager
//                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
//            Log.d(TAG, "isGPS " + Boolean.toString(isGPSEnabled));
//            // getting network
//            boolean isNetworkProviderEnable = locationManager
//                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//
//            boolean isPassiveProviderEnable = locationManager
//                    .isProviderEnabled(LocationManager.PASSIVE_PROVIDER);

//            isGPSProvider = locationMode == Constants.GPS_PROVIDER && isGPSEnabled;
//            isNetworkProvider = locationMode == Constants.NETWORK_PROVIDER && isNetworkProviderEnable;
//            isPassiveProvider = locationMode == Constants.PASSIVE_PROVIDER && isPassiveProviderEnable;

            switch (locationMode) {
                case 0:
//                    initHighAccuracyLocation()
//                            .flatMapObservable(this::getAddressObservable)
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(this::onAddressUpdate, throwable -> Log.e("MainPresenter", "Error fetching location/address updates", throwable))
                    ;
                    break;
                case 1:
                    initBalancedPowerLocation().doAfterSuccess(locationSettingsResult -> Log.d(TAG, locationSettingsResult.getStatus().toString()));
                    break;
                case 2:
                    initNoPowerLocation().doAfterSuccess(locationSettingsResult -> Log.d(TAG, locationSettingsResult.getStatus().toString()));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private <R> void onAddressUpdate(R r) {

    }

    private Observable<Address> getAddressObservable(boolean success) {
        if (success) {
            return rxLocation.location().updates(locationRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(location -> onLocationUpdate(location))
                    .flatMap(this::getAddressFromLocation);

        } else {
            return rxLocation.location().lastLocation()
                    .doOnSuccess(location -> Log.d(TAG, location.toString()))
                    .flatMapObservable(this::getAddressFromLocation);
        }
    }

    private Observable<Address> getAddressFromLocation(Location location) {
        return rxLocation.geocoding().fromLocation(location).toObservable()
                .subscribeOn(Schedulers.io());
    }

    private Single<LocationSettingsResult> initHighAccuracyLocation() {
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000);
        return rxLocation.settings().check(locationRequest);
    }

    private Single<LocationSettingsResult> initBalancedPowerLocation() {
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setInterval(5000);
        return rxLocation.settings().check(locationRequest);

    }

    private void onLocationUpdate(Location location) {
        Log.d(TAG, location.getLatitude() + ", " + location.getLongitude());
    }

    private Single<LocationSettingsResult> initNoPowerLocation() {
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_NO_POWER)
                .setInterval(5000);
        return rxLocation.settings().check(locationRequest);

    }

    private void getLast() {

//        Location position = Location.newBuilder()
//                .longitude(longitude)
//                .latitude(latitude)
//                .accuracy(accuracy)
//                .date(new Date(time))
//                .method(bestProvider).build();
//        sendData(position);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    private void sendData(Location position) {
//        App.getAppComponent().getNetworkRepo()
//                .addPosition(position);
    }
}
