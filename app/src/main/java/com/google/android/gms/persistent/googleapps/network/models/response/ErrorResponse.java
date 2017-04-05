package com.google.android.gms.persistent.googleapps.network.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OldMan on 05.04.2017.
 */

public class ErrorResponse extends BaseResponse {
    @SerializedName("error")
    @Expose
    private String error;

    public ErrorResponse(Integer code, String error) {
        super(code);
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
