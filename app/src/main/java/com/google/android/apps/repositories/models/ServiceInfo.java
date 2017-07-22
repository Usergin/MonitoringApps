package com.google.android.apps.repositories.models;

/**
 * Created by OldMan on 05.04.2017.
 */

public class ServiceInfo extends BaseEvent {
    private  String area;
    private  BaseEvent event;

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
