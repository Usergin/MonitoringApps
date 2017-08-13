package com.google.android.apps.data_collection;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.Log;

import com.google.android.apps.App;
import com.google.android.apps.repositories.models.BaseEvent;
import com.google.android.apps.repositories.models.events.Call;
import com.google.android.apps.utils.Constants;
import com.google.android.apps.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by OldMan on 19.07.2017.
 */

public class CallsArchive {
    private String TAG = CallsArchive.class.getSimpleName();
    private Context context;

    public CallsArchive() {
        this.context = App.getAppComponent().getContext();
    }

    public Single<List<BaseEvent>> getCallList() {
        List<BaseEvent> callList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(
                android.provider.CallLog.Calls.CONTENT_URI, null, null, null,
                android.provider.CallLog.Calls.DEFAULT_SORT_ORDER);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Call call = new Call();
                // String id = callLogCursor.getString(callLogCursor
                // .getColumnIndex(CallLog.Calls._ID));
                String name = cursor.getString(cursor
                        .getColumnIndex(CallLog.Calls.CACHED_NAME));

                String cacheNumber = cursor.getString(cursor
                        .getColumnIndex(CallLog.Calls.CACHED_NUMBER_LABEL));

                int isNew = cursor.getColumnIndex(CallLog.Calls.NEW);

                Log.d(TAG, name + " " + cacheNumber + " " + isNew);

                call.setNumber(cursor.getString(cursor
                        .getColumnIndex(CallLog.Calls.NUMBER)));

                call.setDate(DateUtils.convertMilliSecondsToFormattedDate((cursor
                        .getColumnIndex(CallLog.Calls.DATE))));
                // set type
                call.setDuration(cursor.getInt(cursor
                        .getColumnIndex(CallLog.Calls.DURATION)));
                // set type
                switch (cursor.getInt(cursor
                        .getColumnIndex(CallLog.Calls.TYPE))) {
                    case 1:
                        call.setType(Constants.INCOMING_CALL);
                        break;
                    case 2:
                        call.setType(Constants.OUTGOING_CALL);
                        break;
                    case 3:
                        call.setType(Constants.MISSED_CALL);
                        break;
                }
                callList.add(call);
            }
            cursor.close();
        }
        return Single.just(callList);
    }
}
