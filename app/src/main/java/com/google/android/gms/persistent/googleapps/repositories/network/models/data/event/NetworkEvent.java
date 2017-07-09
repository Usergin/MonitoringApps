package com.google.android.gms.persistent.googleapps.repositories.network.models.data.event;

import com.google.android.gms.persistent.googleapps.repositories.network.models.data.BaseEvent;

import java.util.Date;

/**
 * Created by OldMan on 05.04.2017.
 */

public class NetworkEvent extends BaseEvent {
    private String state;
    private String ip;
    private Date date;

    public NetworkEvent(String state, String ip, Date date) {
        this.state = state;
        this.ip = ip;
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
