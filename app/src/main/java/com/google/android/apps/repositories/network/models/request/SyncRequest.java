package com.google.android.apps.repositories.network.models.request;

/**
 * Created by OldMan on 05.04.2017.
 */

public class SyncRequest extends BaseRequest{
    public SyncRequest(String imei, String device) {
        super(imei, device);
    }
}
