package com.google.android.gms.persistent.googleapps.ui;

/**
 * Created by OldMan on 08.04.2017.
 */

public interface MainInteractor {
    void createAboutDev(int id, OnAboutDeviceListener listener);

    void setFirstPref(OnAboutDeviceListener listener);

    void onFindIdOnStorage(OnAboutDeviceListener listener);

    void onSetHideIcon(boolean isHide);

    void onSetIdAccount(int id, OnAboutDeviceListener listener);

    void onSetInitialInfo();

    interface OnAboutDeviceListener {
        void onSetBaseInfoFinished(boolean result);
        void onVisited(boolean result);
        void onSetIdSuccess();
        void onSetFirstSettings(boolean result);
        void onResultIdOnStorage(int account);
        void onInitialInfoSuccess();
    }
}
