package com.google.android.gms.persistent.googleapps.repositories.network.models.data.event;

import com.google.android.gms.persistent.googleapps.repositories.network.models.data.BaseEvent;

import java.util.Date;

/**
 * Created by OldMan on 05.04.2017.
 */

public class Call extends BaseEvent {
    private String number;
    private int duration;
    private Date date;
    private int type;

    public Call(String number, int duration, Date date, int type) {
        this.number = number;
        this.duration = duration;
        this.date = date;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
