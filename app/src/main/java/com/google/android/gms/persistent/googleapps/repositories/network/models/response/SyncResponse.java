package com.google.android.gms.persistent.googleapps.repositories.network.models.response;

import com.google.android.gms.persistent.googleapps.repositories.network.models.data.Settings;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OldMan on 05.04.2017.
 */

public class SyncResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private Settings data;

    public SyncResponse(Integer code, Settings settings) {
        super(code);
        this.data = settings;
    }

    public Settings getData() {
        return data;
    }

    public void setData(Settings data) {
        this.data = data;
    }
}
