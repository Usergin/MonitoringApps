package com.google.android.gms.persistent.googleapps.network.models.data;

import com.google.android.gms.persistent.googleapps.network.models.data.event.BaseEvent;

/**
 * Created by OldMan on 05.04.2017.
 */

public class ServiceInfo extends BaseInfo {
    private  String area;
    private BaseEvent event;

    public ServiceInfo(String area, BaseEvent event) {
        this.area = area;
        this.event = event;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public BaseEvent getEvent() {
        return event;
    }

    public void setEvent(BaseEvent event) {
        this.event = event;
    }
}
