package com.google.android.gms.persistent.googleapps.network.models.data.event;

import com.google.android.gms.persistent.googleapps.network.models.data.BaseEvent;

import java.util.Date;

/**
 * Created by oldman on 05.07.17.
 */

public class ServiceEvent extends BaseEvent {
    private String area;
    private String event;
    private Date date;

    public ServiceEvent(String area, String event, Date date) {
        this.area = area;
        this.event = event;
        this.date = date;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
