package com.google.android.apps.repositories.network.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OldMan on 05.04.2017.
 */

public class InitialResponse extends BaseResponse {
    @SerializedName("device")
    @Expose
    private String device;
    @SerializedName("error")
    @Expose
    private int error;

    public InitialResponse(Integer code, String device, int error) {
        super(code);
        this.device = device;
        this.error = error;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
