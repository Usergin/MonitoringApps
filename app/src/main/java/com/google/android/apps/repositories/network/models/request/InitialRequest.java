package com.google.android.apps.repositories.network.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OldMan on 04.04.2017.
 */

public class InitialRequest {
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("version_os")
    @Expose
    private String version_os;

    public InitialRequest(String imei) {
        this.imei = imei;
        this.model = android.os.Build.MODEL;
        this.version_os = android.os.Build.VERSION.RELEASE;
    }

    public String getVersion_os() {return version_os; }

    public void setVersion_os(String version_os) {this.version_os = version_os; }

     public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
