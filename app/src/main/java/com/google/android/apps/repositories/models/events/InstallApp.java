package com.google.android.apps.repositories.models.events;

import com.google.android.apps.repositories.models.BaseEvent;

import java.util.Date;

/**
 * Created by OldMan on 05.04.2017.
 */

public class InstallApp extends BaseEvent {
    private String name;
    private String info;
    private Date date;

    public Date getDate() {return date;}

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
