package com.google.android.gms.persistent.googleapps.network.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OldMan on 04.04.2017.
 */

public class StatusResponse {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("device")
    @Expose
    private String device;

    public StatusResponse(Integer code, String device, String error) {
        this.code = code;
        this.device = device;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

}
