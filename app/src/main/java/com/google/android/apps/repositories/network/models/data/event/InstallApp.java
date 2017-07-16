package com.google.android.apps.repositories.network.models.data.event;

import java.util.Date;

/**
 * Created by OldMan on 05.04.2017.
 */

public class InstallApp {
    private String name;
    private String info;
    private Date date;

//    public InstallApp(String name, String info, Date date) {
//        this.name = name;
//        this.info = info;
//        this.date = date;
//    }

    public Date getDate() {return date;
    }

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
