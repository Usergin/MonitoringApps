package com.google.android.gms.persistent.googleapps.network.models.data.event;

import com.google.android.gms.persistent.googleapps.network.models.data.BaseEvent;

import java.util.Date;

/**
 * Created by OldMan on 05.04.2017.
 */

public class Message extends BaseEvent {
    private String number;
    private String data;
    private Date date;
    private int type;

    public Message(String number, String data, Date date, int type) {
        this.number = number;
        this.data = data;
        this.date = date;
        this.type = type;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
