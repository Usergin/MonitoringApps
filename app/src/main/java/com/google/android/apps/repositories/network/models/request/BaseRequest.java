package com.google.android.apps.repositories.network.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OldMan on 05.04.2017.
 */

public class BaseRequest {
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("device")
    @Expose
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
