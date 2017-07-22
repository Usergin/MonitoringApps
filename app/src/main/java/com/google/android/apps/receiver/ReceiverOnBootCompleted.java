package com.google.android.apps.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.google.android.apps.App;
import com.google.android.apps.service.LocationService;
import com.google.android.apps.service.SyncService;

import timber.log.Timber;

public class ReceiverOnBootCompleted extends WakefulBroadcastReceiver {
    private final String TAG = ReceiverOnBootCompleted.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "ReceiverOnBootCompleted");
        Log.d(TAG, "intent " + intent.getAction());
        switch (intent.getAction()) {
            case Intent.ACTION_BOOT_COMPLETED:
                Log.d(TAG, "ACTION_BOOT_COMPLETED");
                Intent syncIntent = new Intent(context, SyncService.class);
                Intent locationIntent = new Intent(context, LocationService.class);
                startWakefulService(context,syncIntent);
                startWakefulService(context,locationIntent);
                break;
            case Intent.ACTION_SHUTDOWN:
                Log.d(TAG, "ACTION_SHUTDOWN");
                break;
            case Intent.ACTION_AIRPLANE_MODE_CHANGED:
                Log.d(TAG, "ACTION_AIRPLANE_MODE_CHANGED");
                break;
            case Intent.ACTION_SCREEN_ON:
                Log.d(TAG, "ACTION_SCREEN_ON");
                App.setIsScreenOn(true);
                break;
            case Intent.ACTION_SCREEN_OFF:
                Log.d(TAG, "ACTION_SCREEN_OFF");
                App.setIsScreenOn(false);
                break;
            case Intent.ACTION_POWER_CONNECTED:
                Log.d(TAG, "ACTION_POWER_CONNECTED");
                break;
            case Intent.ACTION_POWER_DISCONNECTED:
                Log.d(TAG, "ACTION_POWER_DISCONNECTED");
                break;
            case Intent.ACTION_BATTERY_LOW:
                Log.d(TAG, "ACTION_BATTERY_LOW");
                break;
            case Intent.ACTION_BATTERY_OKAY:
                Log.d(TAG, "ACTION_BATTERY_OKAY");
                break;
            case Intent.ACTION_REBOOT:
                Log.d(TAG, "REBOOT");
                break;
            default:
                Log.d(TAG, "intent.getAction()" + intent.getAction());
                break;
        }

//        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
//            Intent pushIntent = new Intent(context, SyncService.class);
//            context.startService(pushIntent);
//        }
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
