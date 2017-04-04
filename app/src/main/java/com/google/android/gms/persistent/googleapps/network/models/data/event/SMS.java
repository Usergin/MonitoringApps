package com.google.android.gms.persistent.googleapps.network.models.data.event;

import com.google.android.gms.persistent.googleapps.network.models.data.BaseInfo;

/**
 * Created by OldMan on 05.04.2017.
 */

public class SMS extends BaseInfo {
    private String number;
    private String data;
    private long date;

    public SMS(String number, String data, long date) {
        this.number = number;
        this.data = data;
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
