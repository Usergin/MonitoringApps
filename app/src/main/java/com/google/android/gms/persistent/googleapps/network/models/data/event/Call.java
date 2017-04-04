package com.google.android.gms.persistent.googleapps.network.models.data.event;

import com.google.android.gms.persistent.googleapps.network.models.data.BaseInfo;

/**
 * Created by OldMan on 05.04.2017.
 */

public class Call extends BaseInfo {
    private String number;
    private String duration;
    private long date;

    public Call(String number, String duration, long date) {
        this.number = number;
        this.duration = duration;
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public long getDate() {return date;}

    public void setDate(long date) {this.date = date;}
}
