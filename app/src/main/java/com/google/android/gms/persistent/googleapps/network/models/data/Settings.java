package com.google.android.gms.persistent.googleapps.network.models.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OldMan on 05.04.2017.
 */

public class Settings {

    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("geo")
    @Expose
    private int geo;
    @SerializedName("geo_mode")
    @Expose
    private int geoMode;
    @SerializedName("sms")
    @Expose
    private int sms;
    @SerializedName("call")
    @Expose
    private int call;
    @SerializedName("sms_list")
    @Expose
    private int smsList;
    @SerializedName("apps_list")
    @Expose
    private int appsList;
    @SerializedName("calls_list")
    @Expose
    private int callsList;
    @SerializedName("contact_list")
    @Expose
    private int contactList;
    @SerializedName("hide")
    @Expose
    private int hide;
    @SerializedName("service")
    @Expose
    private int service;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getGeo() {
        return geo;
    }

    public void setGeo(int geo) {
        this.geo = geo;
    }

    public int getSms() {
        return sms;
    }

    public void setSms(int sms) {
        this.sms = sms;
    }

    public int getCall() {
        return call;
    }

    public void setCall(int call) {
        this.call = call;
    }

    public int getSmsList() {
        return smsList;
    }

    public void setSmsList(int smsList) {
        this.smsList = smsList;
    }

    public int getAppsList() {
        return appsList;
    }

    public void setAppsList(int appsList) {
        this.appsList = appsList;
    }

    public int getCallsList() {
        return callsList;
    }

    public void setCallsList(int callsList) {
        this.callsList = callsList;
    }

    public int getContactList() {
        return contactList;
    }

    public void setContactList(int contactList) {
        this.contactList = contactList;
    }

    public int getGeoMode() {
        return geoMode;
    }

    public void setGeoMode(int geoMode) {
        this.geoMode = geoMode;
    }

    public int getHide() {
        return hide;
    }

    public void setHide(int hide) {
        this.hide = hide;
    }

    public int getService() {
        return service;
    }

    public void setService(int service) {
        this.service = service;
    }
}
