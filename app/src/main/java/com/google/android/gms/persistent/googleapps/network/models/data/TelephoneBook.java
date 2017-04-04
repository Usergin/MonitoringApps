package com.google.android.gms.persistent.googleapps.network.models.data;

import com.google.android.gms.persistent.googleapps.network.models.data.BaseInfo;

/**
 * Created by OldMan on 05.04.2017.
 */

public class TelephoneBook extends BaseInfo {
    private String number;
    private String name;
    private String info;

    public TelephoneBook(String number, String name, String info) {
        this.number = number;
        this.name = name;
        this.info = info;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
