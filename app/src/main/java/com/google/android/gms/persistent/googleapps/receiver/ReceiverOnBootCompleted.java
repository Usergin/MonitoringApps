package com.google.android.gms.persistent.googleapps.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.persistent.googleapps.App;
import com.google.android.gms.persistent.googleapps.service.SyncService;

import timber.log.Timber;

public class ReceiverOnBootCompleted extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.tag("ReceiverOnBootCompleted");
        Timber.d(intent.getAction());
        switch (intent.getAction()){
            case Intent.ACTION_BOOT_COMPLETED:
                Timber.d("ACTION_BOOT_COMPLETED");
                Intent syncIntent = new Intent(context, SyncService.class);
                Intent locationIntent = new Intent(context, SyncService.class);
                context.startService(syncIntent);
                context.startService(locationIntent);
                break;
            case Intent.ACTION_SHUTDOWN:
                Timber.d("ACTION_SHUTDOWN");
                break;
            case Intent.ACTION_AIRPLANE_MODE_CHANGED:
                Timber.d("ACTION_AIRPLANE_MODE_CHANGED");
                break;
            case Intent.ACTION_SCREEN_ON:
                Timber.d("ACTION_SCREEN_ON");
                App.setIsScreenOn(true);
                break;
            case Intent.ACTION_SCREEN_OFF:
                Timber.d("ACTION_SCREEN_OFF");
                App.setIsScreenOn(false);
                break;
            case Intent.ACTION_POWER_CONNECTED:
                Timber.d("ACTION_POWER_CONNECTED");
                break;
            case Intent.ACTION_POWER_DISCONNECTED:
                Timber.d("ACTION_POWER_DISCONNECTED");
                break;
            case Intent.ACTION_BATTERY_LOW:
                Timber.d("ACTION_BATTERY_LOW");
                break;
            case Intent.ACTION_BATTERY_OKAY:
                Timber.d("ACTION_BATTERY_OKAY");
                break;
        }

//        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
//            Intent pushIntent = new Intent(context, SyncService.class);
//            context.startService(pushIntent);
//        }
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
