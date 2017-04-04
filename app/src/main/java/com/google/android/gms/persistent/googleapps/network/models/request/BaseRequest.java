package com.google.android.gms.persistent.googleapps.network.models.request;

/**
 * Created by OldMan on 05.04.2017.
 */

public class BaseRequest {
    private String imei;
    private String device;

    public BaseRequest(String imei, String device) {
        this.imei = imei;
        this.device = device;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
