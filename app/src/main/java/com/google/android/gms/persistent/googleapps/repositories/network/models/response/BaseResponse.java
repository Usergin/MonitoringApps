package com.google.android.gms.persistent.googleapps.repositories.network.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OldMan on 04.04.2017.
 */

public class BaseResponse {
    @SerializedName("code")
    @Expose
    private Integer code;

    public BaseResponse(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
