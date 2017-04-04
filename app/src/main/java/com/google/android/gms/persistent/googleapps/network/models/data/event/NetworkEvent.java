package com.google.android.gms.persistent.googleapps.network.models.data.event;

/**
 * Created by OldMan on 05.04.2017.
 */

public class NetworkEvent extends BaseEvent{
    private String state;
    private String ip;

    public NetworkEvent(String state, String ip) {
        this.state = state;
        this.ip = ip;
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
}
