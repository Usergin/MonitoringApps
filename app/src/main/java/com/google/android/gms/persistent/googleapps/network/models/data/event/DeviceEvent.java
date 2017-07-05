package com.google.android.gms.persistent.googleapps.network.models.data.event;

import java.util.Date;

/**
 * Created by OldMan on 05.04.2017.
 */

public class DeviceEvent extends BaseEvent {
    private String status;
    private Date date;

    public DeviceEvent(String status, Date date) {
        this.status = status;
        this.date = date;
    }

    public DeviceEvent() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
