package com.google.android.gms.persistent.googleapps.network.models.response;

/**
 * Created by OldMan on 05.04.2017.
 */

public class SyncResponse extends BaseResponse {
    private Settings settings;

    public SyncResponse(Integer code, Settings settings) {
        super(code);
        this.settings = settings;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
