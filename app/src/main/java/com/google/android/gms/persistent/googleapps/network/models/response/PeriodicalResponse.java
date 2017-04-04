package com.google.android.gms.persistent.googleapps.network.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OldMan on 05.04.2017.
 */

public class PeriodicalResponse {
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("geo")
    @Expose
    private int geo;
    @SerializedName("sms")
    @Expose
    private int sms;
    @SerializedName("call")
    @Expose
    private int call;
    @SerializedName("sms_list")
    @Expose
    private int sms_list;
    @SerializedName("apps_list")
    @Expose
    private int apps_list;
    @SerializedName("calls_list")
    @Expose
    private int calls_list;
    @SerializedName("contact_list")
    @Expose
    private int contact_list;

    public PeriodicalResponse(int code, int geo, int sms, int call, int sms_list, int apps_list,
                              int calls_list, int contact_list) {
        this.code = code;
        this.geo = geo;
        this.sms = sms;
        this.call = call;
        this.sms_list = sms_list;
        this.apps_list = apps_list;
        this.calls_list = calls_list;
        this.contact_list = contact_list;
    }

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

    public int getSms_list() {
        return sms_list;
    }

    public void setSms_list(int sms_list) {
        this.sms_list = sms_list;
    }

    public int getApps_list() {
        return apps_list;
    }

    public void setApps_list(int apps_list) {
        this.apps_list = apps_list;
    }

    public int getCalls_list() {
        return calls_list;
    }

    public void setCalls_list(int calls_list) {
        this.calls_list = calls_list;
    }

    public int getContact_list() {
        return contact_list;
    }

    public void setContact_list(int contact_list) {
        this.contact_list = contact_list;
    }
}
