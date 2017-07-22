package com.google.android.apps.repositories.models.events;

import com.google.android.apps.repositories.models.BaseEvent;
import java.util.Date;

/**
 * Created by OldMan on 05.04.2017.
 */

public class Message extends BaseEvent {
    private String number;
    private String data;
    private Date date;
    private int type;

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
