package com.google.android.gms.persistent.googleapps.network.models.data.event;

/**
 * Created by OldMan on 05.04.2017.
 */

public class DeviceEvent extends BaseEvent {
    private String status;

    public DeviceEvent(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
