package com.google.android.gms.persistent.googleapps.network.models.request;

/**
 * Created by OldMan on 04.04.2017.
 */

public class InitialRequest {
    private String imei;
    private String model;

    public InitialRequest(String imei, String model) {
        this.imei = imei;
        this.model = model;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
