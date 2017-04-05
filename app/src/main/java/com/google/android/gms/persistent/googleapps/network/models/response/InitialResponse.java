package com.google.android.gms.persistent.googleapps.network.models.response;

/**
 * Created by OldMan on 05.04.2017.
 */

public class InitialResponse extends BaseResponse {
    private String device;

    public InitialResponse(Integer code, String device) {
        super(code);
        this.device = device;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
