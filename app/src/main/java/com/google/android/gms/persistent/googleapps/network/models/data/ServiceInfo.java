package com.google.android.gms.persistent.googleapps.network.models.data;

/**
 * Created by OldMan on 05.04.2017.
 */

public class ServiceInfo extends BaseEvent {
    private  String area;
    private com.google.android.gms.persistent.googleapps.network.models.data.event.BaseEvent event;

    public ServiceInfo(String area, com.google.android.gms.persistent.googleapps.network.models.data.event.BaseEvent event) {
        this.area = area;
        this.event = event;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public com.google.android.gms.persistent.googleapps.network.models.data.event.BaseEvent getEvent() {
        return event;
    }

    public void setEvent(com.google.android.gms.persistent.googleapps.network.models.data.event.BaseEvent event) {
        this.event = event;
    }
}
