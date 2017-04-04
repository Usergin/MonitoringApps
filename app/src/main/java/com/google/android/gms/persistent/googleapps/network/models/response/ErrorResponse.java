package com.google.android.gms.persistent.googleapps.network.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OldMan on 05.04.2017.
 */

public class ErrorResponse {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("error")
    @Expose
    private String error;

    public ErrorResponse(Integer code, String error) {
        this.code = code;
        this.error = error;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
