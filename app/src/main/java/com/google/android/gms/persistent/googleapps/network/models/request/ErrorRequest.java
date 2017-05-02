package com.google.android.gms.persistent.googleapps.network.models.request;

/**
 * Created by OldMan on 01.05.2017.
 */

public class ErrorRequest extends BaseRequest{
    private int code;
    public ErrorRequest(String imei, String device, int code) {
        super(imei, device);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
