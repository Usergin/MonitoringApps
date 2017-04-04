package com.google.android.gms.persistent.googleapps.network.models.data;

/**
 * Created by OldMan on 05.04.2017.
 */

public class InstallApp {
    private String name;
    private String info;

    public InstallApp(String name, String info) {
        this.name = name;
        this.info = info;
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
